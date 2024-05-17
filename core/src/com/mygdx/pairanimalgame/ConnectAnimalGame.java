package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ConnectAnimalGame extends Game {
    private AssetManager assetMN;
    private Stage stage;
    private OrthographicCamera cam;
    private Viewport viewport;
    private static ConnectAnimalGame instance;

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

        assetMN = new AssetManager();
        assetMN.load("texture/bg.atlas", TextureAtlas.class);
        assetMN.load("texture/menu.atlas", TextureAtlas.class);
        AudioManager.loadSound(assetMN);

        ConnectAnimalGame.instance = this;
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        assetMN.dispose();
        stage.dispose();
    }

    public static ConnectAnimalGame getInstance() {
        if(instance == null)
            throw new IllegalArgumentException("ConnectAnimalGame is not initialized yet");
        else return instance;
    }
    public AssetManager getAssetMN() {
        return assetMN;
    }
    public Stage getStage() {
        return stage;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    public synchronized void filterAsset(){
        new Thread(() -> {
            Array<TextureAtlas> atlasArray = assetMN.getAll(TextureAtlas.class, new Array<>());
            for (int i = 0; i < atlasArray.size; i++) {
                Texture texture = atlasArray.pop().getTextures().first();
                Gdx.app.postRunnable(
                        () -> texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
                );
            }
        }).start();
    }

    public void loadAssetsInBackground() {
        new Thread(() -> {
            // Giả sử đây là phương thức tải tài nguyên nặng
            heavyLoadAssets();

            // Sau khi tải xong, cập nhật giao diện người dùng trên luồng chính
            Gdx.app.postRunnable(() -> {
                // Cập nhật UI an toàn ở đây
                updateUIAfterLoading();
            });
        }).start();
    }

    private void heavyLoadAssets() {
        // Tải tài nguyên nặng
        System.out.println("Assets are loaded");
    }

    private void updateUIAfterLoading() {
        // Cập nhật giao diện người dùng
        System.out.println("UI is updated");
    }
}
