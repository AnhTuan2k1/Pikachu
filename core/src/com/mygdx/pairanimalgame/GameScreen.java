package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
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
    EffectMN effectMN;

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
        assetMN.load("texture/play.atlas", TextureAtlas.class);
        assetMN.load("effect/firework.p", ParticleEffect.class);
        assetMN.load("effect/laze.p", ParticleEffect.class);



    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        if(assetMN.update()){
            if(playAtlas == null){
                playAtlas = assetMN.get("texture/play.atlas", TextureAtlas.class);
            }
            if(animalAtlas == null){
                animalAtlas = assetMN.get("texture/animal.atlas", TextureAtlas.class);
                createAnimalMatrix();
            }
            if(effectMN == null){
                effectMN = new EffectMN(assetMN);
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
        effectMN.dispose();
    }

    private void createAnimalMatrix() {
        TextureRegion selected = new TextureRegion(animalAtlas.findRegion("selected"));
        TextureRegion cucxilau = new TextureRegion(animalAtlas.findRegion("cucxilau1"));
        TextureRegion[] animalRegions = new TextureRegion[50];
        for (int i = 0; i<animalRegions.length;i++){
            animalRegions[i] = new TextureRegion(animalAtlas.findRegion(String.valueOf(i)));
        }

        int r = 3, c = 6;
        AnimalCard[][] matrix = new AnimalCard[r+2][c+2];
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
                            stage.addActor(effectMN.getFireworkEffectActor(animalCard));
                            stage.addActor(effectMN.getFireworkEffectActor(preAnimal));
                            preAnimal = null;

                            Pikachu.foundPath.print();
                            if(Pikachu.isWin(matrix)) createAnimalMatrix();
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

    private void reCreateAnimalMatrix() {

    }
}

enum AnimalMatrixBehaviour{

}
