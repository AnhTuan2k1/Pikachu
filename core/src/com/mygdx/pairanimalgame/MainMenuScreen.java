package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private final ConnectAnimalGame game;  // Tham chiếu đến lớp Game để chuyển màn hình
    private final AssetManager assetMN;
    private final Stage stage;
    private TextureAtlas playAtlas;
    private TextureAtlas menuAtlas;
    private TextureAtlas commonAtlas;
    private PopupDialog noticeWindow;

    public MainMenuScreen(ConnectAnimalGame game) {
        System.out.println("MainMenuScreen create");
        this.game = game;
        this.assetMN = game.getAssetMN();
        this.stage = game.getStage();
    }

    @Override
    public void show() {
        System.out.println("MainMenuScreen show");
        assetMN.finishLoading();

        playAtlas = assetMN.get("texture/play.atlas", TextureAtlas.class);
        menuAtlas = assetMN.get("texture/menu.atlas", TextureAtlas.class);
        commonAtlas = assetMN.get("texture/common.atlas", TextureAtlas.class);
        TextureAtlas bgAtlas = assetMN.get("texture/bg.atlas", TextureAtlas.class);

        stage.addActor(new BackGround(stage, bgAtlas.findRegion("bg2")));
        stage.addActor(createRemoveDataButton());
        stage.addActor(createNoticeWindow());
        createUI();
        ConnectAnimalGame.getInstance().filterAsset();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        stage.act(delta);
        stage.draw();
    }

    private void createUI() {
        MyLabel bestRank = new MyLabel("Best Rank: " + GameData.getBestRank(), Color.YELLOW);
        bestRank.setPosition(20, stage.getHeight() - 20);
        stage.addActor(bestRank);
        MyLabel highScore = new MyLabel("High Score: " + GameData.getHighScore(), Color.YELLOW);
        highScore.setPosition(20, stage.getHeight() - 50);
        stage.addActor(highScore);


        stage.addActor(new Logo(stage, menuAtlas.findRegion("logo")));
        stage.addActor(new LightTon(stage, menuAtlas.findRegion("lighton0"), menuAtlas.findRegion("lightoff0"), 350));
        stage.addActor(new LightTon(stage, menuAtlas.findRegion("lighton1"), menuAtlas.findRegion("lightoff1"), -370));
        stage.addActor(new LightTon(stage, menuAtlas.findRegion("lighton2"), menuAtlas.findRegion("lightoff2"), 450));

        Table contentTable = new Table();
        MyButton btn;
        btn = new MyButton(commonAtlas.findRegion("btn_yellow"), game, Rank.Rank1.name, noticeWindow);
        contentTable.add(btn).expandX().fill().pad(10);
        contentTable.row();
        btn = new MyButton(commonAtlas.findRegion("btn_yellow"), game, Rank.Rank2.name, noticeWindow);
        contentTable.add(btn).expandX().fill().pad(10);
        contentTable.row();
        btn = new MyButton(commonAtlas.findRegion("btn_yellow"), game, Rank.Rank3.name, noticeWindow);
        contentTable.add(btn).expandX().fill().pad(10);
        contentTable.row();
        btn = new MyButton(commonAtlas.findRegion("btn_red"), game, Rank.Rank4.name, noticeWindow);
        contentTable.add(btn).expandX().fill().pad(10);
        contentTable.row();

        ScrollPane scrollPane = new ScrollPane(contentTable);
        scrollPane.setScrollingDisabled(true, false); // Chỉ cho phép cuộn dọc
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(scrollPane).expandX().fillX().height(stage.getHeight() * 0.6f); // Mở rộng và lấp đầy theo chiều ngang

        stage.addActor(mainTable);
        mainTable.setPosition(0, -stage.getHeight()*0.13f);
    }
    private Actor createRemoveDataButton() {
        PauseButton pauseButton = new PauseButton(playAtlas);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameData.removeAllGamePreferences();
            }
        });
        return pauseButton;
    }

    private Actor createNoticeWindow(){
        // right button for dialog
        MyButton left = new MyButton(commonAtlas.findRegion("btn_red"));
        left.setText("New Game");

        // left button for dialog
        MyButton right = new MyButton(commonAtlas.findRegion("btn_yellow"));
        right.setText("Resume");

        noticeWindow = new PopupDialog(stage, commonAtlas, right, left, "Continue last [] game");
        return noticeWindow;
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("MainMenuScreen resize");
    }

    @Override
    public void pause() {
        System.out.println("MainMenuScreen pause");
    }

    @Override
    public void resume() {
        System.out.println("MainMenuScreen resume");
    }

    @Override
    public void hide() {
        System.out.println("MainMenuScreen hide");
        playAtlas = null;
        stage.clear();
        GameManager.getInstance().clear();
    }

    @Override
    public void dispose() {
        System.out.println("MainMenuScreen dispose");
    }
}
