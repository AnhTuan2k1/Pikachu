package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.LinkedList;
import java.util.Random;

public class AnimalMatrix extends Actor {
    private final Stage stage;
    private final TextureAtlas animalAtlas;
    private final EffectMN effectMN;
    private AnimalMatrixBehaviour matrixBehaviour = AnimalMatrixBehaviour.BOTTOM;
    AnimalCard preAnimal;
    AnimalCard[][] matrix;

    public AnimalMatrix(Stage stage, TextureAtlas animalAtlas, EffectMN effectMN) {
        this.stage = stage;
        this.animalAtlas = animalAtlas;
        this.effectMN = effectMN;

        createAnimalMatrix();
    }

    private void createAnimalMatrix() {
        TextureRegion selected = new TextureRegion(animalAtlas.findRegion("selected"));
        TextureRegion cucxilau = new TextureRegion(animalAtlas.findRegion("cucxilau1"));
        TextureRegion[] animalRegions = new TextureRegion[50];
        for (int i = 0; i<animalRegions.length;i++){
            animalRegions[i] = new TextureRegion(animalAtlas.findRegion(String.valueOf(i)));
        }

        int r = 5, c = 8;
        matrix = new AnimalCard[r+2][c+2];
        int[][] animalType = EvenFrequencyMatrix.generateMatrixWithEvenFrequency(r,c,50);
        EvenFrequencyMatrix.printMatrix(animalType);

        //AnimalCard.width = cucxilau.getRegionWidth() - 10;
        //AnimalCard.height = cucxilau.getRegionHeight() - 10;
        AnimalCard.marginLeft = Gdx.graphics.getWidth()/2 - AnimalCard.width*(c+2)/2;
        AnimalCard.marginBottom = Gdx.graphics.getHeight()/2 - AnimalCard.height*(r+2)/2;


        int type = -1;
        for (int row = 0; row<matrix.length; row++){
            for(int col = 0; col<matrix[0].length; col++){
                // hide animals
                if(row == 0 || col == 0 || row == matrix.length - 1 || col == matrix[0].length - 1){
                    type = 0;
                    TextureRegion an = animalRegions[type];
                    AnimalCard animalCard = new AnimalCard(cucxilau, an, type, selected, row, col);
                    animalCard.setVisible(false);
                    animalCard.setActive(false);
                    matrix[row][col] = animalCard;
                    stage.addActor(animalCard);
                    continue;
                }
                // main animals
                type = animalType[row - 1][col - 1];
                TextureRegion an = animalRegions[type];
                AnimalCard animalCard = new AnimalCard(cucxilau, an, type, selected, row, col);
                animalCard.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(preAnimal == null) {
                            preAnimal = animalCard;
                            preAnimal.setSelected(true);
                        }
                        else if(Pikachu.canMatch(matrix, animalCard.getIndexX(), animalCard.getIndexY(), preAnimal.getIndexX(), preAnimal.getIndexY())){

                            animalCard.setSelected(true);
                            animalCard.setInActive(stage, effectMN);
                            preAnimal.setInActive(stage, effectMN);

                            activeMatrixBehaviour(animalCard);
                            preAnimal = null;

                            Pikachu.foundPath.print();
                            if(Pikachu.isWin(matrix)) reCreateAnimalMatrix();
                            else if(!Pikachu.anyMatchPossible(matrix)) Pikachu.shuffleMatrixExceptInvisible(matrix);

                            //printAnimals(matrix);
                        }
                        else {
                            if(preAnimal == animalCard){
                                boolean selected = preAnimal.isSelected();
                                preAnimal.setSelected(!selected);
                                if(selected) preAnimal = null;
                            }
                            else {
                                preAnimal.setSelected(false);
                                preAnimal = animalCard;
                                preAnimal.setSelected(true);
                            }

                        }
                    }

                });

                matrix[row][col] = animalCard;
                stage.addActor(animalCard);
            }
        }
        if(!Pikachu.anyMatchPossible(matrix)) Pikachu.shuffleMatrixExceptInvisible(matrix);
    }

    private void activeMatrixBehaviour(AnimalCard animalCard) {
        int x1 = preAnimal.getIndexX();
        int y1 = preAnimal.getIndexY();
        int x2 = animalCard.getIndexX();
        int y2 = animalCard.getIndexY();

        if(matrixBehaviour == AnimalMatrixBehaviour.LEFT){
            LinkedList<AnimalCard> animalsX1 = new LinkedList<AnimalCard>();
            LinkedList<AnimalCard> animalsX2 = new LinkedList<AnimalCard>();

            for(int i = 1; i<matrix[0].length - 1; i++){
                if(matrix[x1][i].isActive()) animalsX1.add(matrix[x1][i]);
                if(matrix[x2][i].isActive()) animalsX2.add(matrix[x2][i]);
            }
            for(int i = 1; i<matrix[0].length - 1; i++){
                if(!matrix[x1][i].isActive()) animalsX1.add(matrix[x1][i]);
                if(!matrix[x2][i].isActive()) animalsX2.add(matrix[x2][i]);
            }

            for(int i = 1; i<matrix[0].length - 1; i++){
                AnimalCard animalX1 = animalsX1.poll();
                if (animalX1 != null) {
                    animalX1.setIndex(x1, i,true);
                    matrix[x1][i] = animalX1;
                } else break;

                AnimalCard animalX2 = animalsX2.poll();
                if (animalX2 != null) {
                    animalX2.setIndex(x2, i,true);
                    matrix[x2][i] = animalX2;
                } else break;
            }
        }
        else if(matrixBehaviour == AnimalMatrixBehaviour.RIGHT){
            LinkedList<AnimalCard> animalsX1 = new LinkedList<AnimalCard>();
            LinkedList<AnimalCard> animalsX2 = new LinkedList<AnimalCard>();

            for(int i = 1; i<matrix[0].length - 1; i++){
                if(!matrix[x1][i].isActive()) animalsX1.add(matrix[x1][i]);
                if(!matrix[x2][i].isActive()) animalsX2.add(matrix[x2][i]);
            }
            for(int i = 1; i<matrix[0].length - 1; i++){
                if(matrix[x1][i].isActive()) animalsX1.add(matrix[x1][i]);
                if(matrix[x2][i].isActive()) animalsX2.add(matrix[x2][i]);
            }

            for(int i = matrix[0].length - 2; i>0; i--){
                AnimalCard animalX1 = animalsX1.pollLast();
                if (animalX1 != null) {
                    animalX1.setIndex(x1, i,true);
                    matrix[x1][i] = animalX1;
                } else break;

                AnimalCard animalX2 = animalsX2.pollLast();
                if (animalX2 != null) {
                    animalX2.setIndex(x2, i,true);
                    matrix[x2][i] = animalX2;
                } else break;
            }
        }
        else if(matrixBehaviour == AnimalMatrixBehaviour.TOP){
            LinkedList<AnimalCard> animalsY1 = new LinkedList<AnimalCard>();
            LinkedList<AnimalCard> animalsY2 = new LinkedList<AnimalCard>();

            for(int i = 1; i<matrix.length - 1; i++){
                if(matrix[i][y1].isActive()) animalsY1.add(matrix[i][y1]);
                if(matrix[i][y2].isActive()) animalsY2.add(matrix[i][y2]);
            }
            for(int i = 1; i<matrix.length - 1; i++){
                if(!matrix[i][y1].isActive()) animalsY1.add(matrix[i][y1]);
                if(!matrix[i][y2].isActive()) animalsY2.add(matrix[i][y2]);
            }

            for(int i = 1; i<matrix.length - 1; i++){
                AnimalCard animalY1 = animalsY1.poll();
                if (animalY1 != null) {
                    animalY1.setIndex(i, y1,true);
                    matrix[i][y1] = animalY1;
                } else break;

                AnimalCard animalY2 = animalsY2.poll();
                if (animalY2 != null) {
                    animalY2.setIndex(i, y2,true);
                    matrix[i][y2] = animalY2;
                } else break;
            }
        }
        else if(matrixBehaviour == AnimalMatrixBehaviour.BOTTOM){
            LinkedList<AnimalCard> animalsY1 = new LinkedList<AnimalCard>();
            LinkedList<AnimalCard> animalsY2 = new LinkedList<AnimalCard>();

            for(int i = 1; i<matrix.length - 1; i++){
                if(!matrix[i][y1].isActive()) animalsY1.add(matrix[i][y1]);
                if(!matrix[i][y2].isActive()) animalsY2.add(matrix[i][y2]);
            }

            for(int i = 1; i<matrix.length - 1; i++){
                if(matrix[i][y1].isActive()) animalsY1.add(matrix[i][y1]);
                if(matrix[i][y2].isActive()) animalsY2.add(matrix[i][y2]);
            }

            for(int i = matrix.length - 2; i>0; i--){
                AnimalCard animalY1 = animalsY1.pollLast();
                if (animalY1 != null) {
                    animalY1.setIndex(i, y1,true);
                    matrix[i][y1] = animalY1;
                } else break;

                AnimalCard animalY2 = animalsY2.pollLast();
                if (animalY2 != null) {
                    animalY2.setIndex(i, y2,true);
                    matrix[i][y2] = animalY2;
                } else break;
            }
        }
        else if(matrixBehaviour == AnimalMatrixBehaviour.MIDDLE_VERTICAL){
            //left to middle
            for(int i = matrix.length/2; i>0; i--){
                if(!matrix[x1][i].isActive()){ //get empty position
                    for(int j = i-1; j>0; j--){
                        if(matrix[x1][j].isActive()){   //swap with an value
                            AnimalCard animal = matrix[x1][i];
                            matrix[x1][j].setIndex(x1, i,true);
                            matrix[x1][i].setIndex(x1, j,true);

                            matrix[x1][i] = matrix[x1][j];
                            matrix[x1][j] = animal;
                            break;
                        }
                    }
                }
                if(!matrix[x2][i].isActive()){
                    for(int j = i-1; j>0; j--){
                        if(matrix[x2][j].isActive()){   //swap with an value
                            AnimalCard animal = matrix[x2][i];
                            matrix[x2][j].setIndex(x2, i,true);
                            matrix[x2][i].setIndex(x2, j,true);

                            matrix[x2][i] = matrix[x2][j];
                            matrix[x2][j] = animal;
                            break;
                        }
                    }
                }
            }
            //right to middle
            for(int i = matrix.length/2 + 1; i<matrix[0].length-2; i++){
                if(!matrix[x1][i].isActive()){ //get empty position
                    for(int j = i+1; j<matrix[0].length-1; j++){
                        if(matrix[x1][j].isActive()){   //swap with an value
                            AnimalCard animal = matrix[x1][i];
                            matrix[x1][j].setIndex(x1, i,true);
                            matrix[x1][i].setIndex(x1, j,true);

                            matrix[x1][i] = matrix[x1][j];
                            matrix[x1][j] = animal;
                            break;
                        }
                    }
                }
                if(!matrix[x2][i].isActive()){ //get empty position
                    for(int j = i+1; j<matrix[0].length-1; j++){
                        if(matrix[x2][j].isActive()){   //swap with an value
                            AnimalCard animal = matrix[x2][i];
                            matrix[x2][j].setIndex(x2, i,true);
                            matrix[x2][i].setIndex(x2, j,true);

                            matrix[x2][i] = matrix[x2][j];
                            matrix[x2][j] = animal;
                            break;
                        }
                    }
                }
            }
        }
        else if(matrixBehaviour == AnimalMatrixBehaviour.MIDDLE_HORIZONTAL){
            //top to middle
            for(int i = matrix.length/2; i>0; i--){
                if(!matrix[i][y1].isActive()){ //get empty position
                    for(int j = i-1; j>0; j--){
                        if(matrix[j][y1].isActive()){   //swap with an value
                            AnimalCard animal = matrix[i][y1];
                            matrix[j][y1].setIndex(i, y1,true);
                            matrix[i][y1].setIndex(j, y1,true);

                            matrix[i][y1] = matrix[j][y1];
                            matrix[j][y1] = animal;
                            break;
                        }
                    }
                }
                if(!matrix[i][y2].isActive()){
                    for(int j = i-1; j>0; j--){
                        if(matrix[j][y2].isActive()){   //swap with an value
                            AnimalCard animal = matrix[i][y2];
                            matrix[j][y2].setIndex(i, y2,true);
                            matrix[i][y2].setIndex(j, y2,true);

                            matrix[i][y2] = matrix[j][y2];
                            matrix[j][y2] = animal;
                            break;
                        }
                    }
                }
            }
            //bottom to middle
            for(int i = matrix.length/2 + 1; i<matrix.length-2; i++){
                if(!matrix[i][y1].isActive()){ //get empty position
                    for(int j = i+1; j<matrix.length-1; j++){
                        if(matrix[j][y1].isActive()){   //swap with an value
                            AnimalCard animal = matrix[i][y1];
                            matrix[j][y1].setIndex(i, y1,true);
                            matrix[i][y1].setIndex(j, y1,true);

                            matrix[i][y1] = matrix[j][y1];
                            matrix[j][y1] = animal;
                            break;
                        }
                    }
                }
                if(!matrix[i][y2].isActive()){ //get empty position
                    for(int j = i+1; j<matrix.length-1; j++){
                        if(matrix[j][y2].isActive()){   //swap with an value
                            AnimalCard animal = matrix[i][y2];
                            matrix[j][y2].setIndex(i, y2,true);
                            matrix[i][y2].setIndex(j, y2,true);

                            matrix[i][y2] = matrix[j][y2];
                            matrix[j][y2] = animal;
                            break;
                        }
                    }
                }
            }
        }
        adjustOderDraw();
    }

    private void reCreateAnimalMatrix() {
        AnimalMatrixBehaviour[] behaviour = AnimalMatrixBehaviour.values();
        matrixBehaviour = behaviour[new Random().nextInt(behaviour.length)];
        createAnimalMatrix();
    }

    private void adjustOderDraw(){
        for (AnimalCard[] row : matrix) {
            for (AnimalCard value : row) {
                if(value.isVisible()) value.toFront();
            }
        }
    }

    public static void printMatrix(AnimalCard[][] matrix) {
        System.out.println("-------------------------------");
        for (AnimalCard[] row : matrix) {
            for (AnimalCard value : row) {
                System.out.print(value.getIndexX() + "," + value.getIndexY());
                System.out.print("  ");
            }
        }
    }
    public static void printAnimals(AnimalCard[][] matrix) {
        for (int i = 1; i < matrix.length-1; i++) {
            for (int j = 1; j < matrix[0].length-1; j++) {
                System.out.print(matrix[i][j].getType() + " ");
            }
            System.out.println();
        }
    }
}
enum AnimalMatrixBehaviour{
    NORMAL,
    LEFT,
    RIGHT,
    TOP,
    BOTTOM,
    MIDDLE_VERTICAL,
    MIDDLE_HORIZONTAL
}