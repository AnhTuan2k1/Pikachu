package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Font;

public class LoadingScreen implements Screen {
    private final ConnectAnimalGame game;
    private final AssetManager assetMN;
    private final Stage stage;
    private ProgressBar progressBar;

    public LoadingScreen(ConnectAnimalGame  game) {
        this.game = game;
        this.assetMN = game.getAssetMN();
        this.stage = game.getStage();
    }

    @Override
    public void show() {
        System.out.println("LoadingScreen show");
        assetMN.finishLoading();

        // Tạo một ProgressBar với giá trị min là 0, max là 100, và step là 1
        progressBar = new ProgressBar(0, 100, 1, false, new ProgressBar.ProgressBarStyle());
        progressBar.setValue(0);  // Thiết lập giá trị ban đầu
        progressBar.setBounds(50, 150, 200, 20);  // Đặt vị trí và kích thước
        stage.addActor(progressBar);

        stage.addActor(new BackGround(stage, assetMN.get("texture/bg.atlas", TextureAtlas.class).findRegion("bg2")));
        stage.addActor(new Logo(stage, assetMN.get("texture/menu.atlas", TextureAtlas.class).findRegion("logo")));
        loadAsset();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        if (assetMN.update()) {
            game.setScreen(new MainMenuScreen(game));

            assetMN.load("texture/animal.atlas", TextureAtlas.class);
            assetMN.load("effect/firework.p", ParticleEffect.class);
            assetMN.load("effect/laze.p", ParticleEffect.class);
            assetMN.load("effect/laze2.p", ParticleEffect.class);
            assetMN.load("effect/laser1.png", Texture.class);
            assetMN.load("effect/laser1_2.png", Texture.class);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Mô phỏng việc cập nhật tiến trình
        progressBar.setValue(progressBar.getValue() + 0.1f);
    }

    private void loadAsset() {
        //assetMN.load("texture/bg.atlas", TextureAtlas.class);
        //assetMN.load("texture/menu.atlas", TextureAtlas.class);

        assetMN.load("texture/play.atlas", TextureAtlas.class);
        assetMN.load("texture/common.atlas", TextureAtlas.class);
        assetMN.load("font/comic.fnt", BitmapFont.class);

        //assetMN.load("texture/animal.atlas", TextureAtlas.class);
        //assetMN.load("effect/firework.p", ParticleEffect.class);
        //assetMN.load("effect/laze.p", ParticleEffect.class);
        //assetMN.load("effect/laze2.p", ParticleEffect.class);
        //assetMN.load("assets/effect/laser1.png", Texture.class);
        //assetMN.load("assets/effect/laser1_2.png", Texture.class);
    }

    public void resize(int width, int height) {
        System.out.println("LoadingScreen resize");
    }

    @Override
    public void pause() {
        System.out.println("LoadingScreen pause");
    }

    @Override
    public void resume() {
        System.out.println("LoadingScreen resume");
    }

    @Override
    public void hide() {
        System.out.println("LoadingScreen hide");
        stage.clear();
    }

    @Override
    public void dispose() {
        System.out.println("LoadingScreen dispose");
    }
}
