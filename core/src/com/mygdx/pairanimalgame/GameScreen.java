package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends Game {
    static float height = 0;
    AssetManager assetMN;
    TextureAtlas animalAtlas;
    TextureAtlas playAtlas;
    OrthographicCamera cam;
    Viewport viewport;
    Stage stage;

    AnimalCard preAnimal;
    //AnimalCard[][] matrix;

    @Override
    public void create() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        float worldWidth;
        float worldHeight = 720;
        worldWidth = worldHeight * screenWidth / screenHeight;
        cam = new OrthographicCamera(worldWidth, worldHeight);
        cam.setToOrtho(false);
        viewport = new FitViewport(worldWidth, worldHeight, cam);
        viewport.apply();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        height = stage.getHeight();
        height = Gdx.graphics.getHeight();

        assetMN = new AssetManager();
        assetMN.load("texture/animal.atlas", TextureAtlas.class);
        assetMN.finishLoading();
        animalAtlas = assetMN.get("texture/animal.atlas", TextureAtlas.class);

        assetMN.load("texture/play.atlas", TextureAtlas.class);

        TextureRegion selected = new TextureRegion(animalAtlas.findRegion("selected"));
        TextureRegion cucxilau = new TextureRegion(animalAtlas.findRegion("cucxilau1"));
        TextureRegion[] animalRegions = new TextureRegion[50];
        for (int i = 0; i<animalRegions.length;i++){
            animalRegions[i] = new TextureRegion(animalAtlas.findRegion(String.valueOf(i)));
        }


        //AnimalCard.itemWidth = cucxilau.getRegionWidth() - 10;
        //AnimalCard.itemHeight = cucxilau.getRegionHeight() - 10;
        //AnimalCard.marginLeft = 100;
        //AnimalCard.marginBottom = 100;

        createAnimalMatric(animalRegions, cucxilau, selected);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 1, 1);
        if(playAtlas == null){
            if(assetMN.update()) {
                playAtlas = assetMN.get("texture/play.atlas", TextureAtlas.class);
            }
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        assetMN.dispose();
        animalAtlas.dispose();
        playAtlas.dispose();
        stage.dispose();
    }

    private void createAnimalMatric(TextureRegion[] animalRegions, TextureRegion cucxilau, TextureRegion selected) {
        int r = 7, c = 4;
        AnimalCard[][] matrix = new AnimalCard[r+2][c+2];
        int[][] animalId = EvenFrequencyMatrix.generateMatrixWithEvenFrequency(r,c,50);
        EvenFrequencyMatrix.printMatrix(animalId);
        int id = -1;
        for (int row = 0; row<matrix.length; row++){
            for(int col = 0; col<matrix[0].length; col++){

                // hide animals
                if(row == 0 || col == 0 || row == matrix.length - 1 || col == matrix[0].length - 1){
                    id = 0;
                    TextureRegion an = animalRegions[id];
                    AnimalCard animalCard = new AnimalCard(cucxilau, an, id, selected, row, col);
                    animalCard.setVisible(false);
                    matrix[row][col] = animalCard;
                    stage.addActor(animalCard);
                    continue;
                }

                // main animals
                id = animalId[row - 1][col - 1];
                TextureRegion an = animalRegions[id];
                AnimalCard animalCard = new AnimalCard(cucxilau, an, id, selected, row, col);
                animalCard.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(preAnimal == null) {
                            preAnimal = animalCard;
                            preAnimal.setSelected(true);
                        }
                        else if(Pikachu.canMatch(matrix, animalCard.getIndexX(), animalCard.getIndexY(), preAnimal.getIndexX(), preAnimal.getIndexY())){
                            animalCard.setVisible(false);
                            preAnimal.setVisible(false);
                            preAnimal = null;

                            if(Pikachu.isWin(matrix)) createAnimalMatric(animalRegions, cucxilau, selected);
                            else if(!Pikachu.anyMatchPossible(matrix)) Pikachu.shuffleMatrixExceptInvisible(matrix);
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
}
