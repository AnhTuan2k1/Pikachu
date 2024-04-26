package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.Random;

public class GameScreen extends Game {
    static float height = 0;
    AssetManager assetMN;
    TextureAtlas animalAtlas;
    TextureAtlas playAtlas;
    OrthographicCamera cam;
    Viewport viewport;
    Stage stage;
    EffectMN effectMN;
    AnimalMatrix animalMatrix;

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
            }
            if(effectMN == null){
                effectMN = new EffectMN(assetMN);
            }

            if(animalMatrix == null){
                animalMatrix = new AnimalMatrix(stage, animalAtlas,effectMN);
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

}
