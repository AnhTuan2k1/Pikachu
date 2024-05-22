package com.mygdx.pairanimalgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FailPopup extends Group {
    private final Image bg;
    private final TextureRegion fail;
    private final MyButton exit;
    private final MyButton replay;
    private final float scale;
    private final Stage stage;
    private BitmapFont failFont;
    private BitmapFont valueFont;
    private int score;
    private int level;

    public FailPopup(Stage stage, AssetManager assetManager, int score) {
        this.fail = assetManager.get("texture/play.atlas", TextureAtlas.class)
                .findRegion("levelfail");
        this.exit = new MyButton(assetManager.get("texture/common.atlas", TextureAtlas.class)
                .findRegion("btn_yellow")).setText("Exit");
        this.replay = new MyButton(assetManager.get("texture/common.atlas", TextureAtlas.class)
                .findRegion("btn_red")).setText("Replay");
        this.stage = stage;
        this.bg = new Image(createBlackTexture());
        this.failFont = ConnectAnimalGame.getInstance().getAssetMN().get("font/comic.fnt");
        this.score = score;

        scale = stage.getWidth()*0.4f / fail.getRegionWidth();
        float width = stage.getWidth()*0.55f;
        float height = stage.getHeight()*0.65f;

        setBounds(stage.getWidth()/2-width/2, stage.getHeight()/2 - height/2, width, height);
        setStage(stage);
        setVisible(false);

        initialize();
    }

    private void initialize() {
        stage.addActor(bg);

        Image fa = new Image(fail);
        fa.setSize(fa.getWidth()*scale, fa.getHeight()*scale);
        fa.setPosition(getWidth()/2-fa.getWidth()/2,getHeight()-fa.getHeight());
        addActor(fa);

        addActor(exit);
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.getInstance().getAnimalMatrix().matrix = null;
                ConnectAnimalGame.getInstance().setScreen(new MainMenuScreen(ConnectAnimalGame.getInstance()));
            }
        });
        //exit.setSize(exit.getWidth()*0.7f, exit.getHeight()*0.7f);

        addActor(replay);
        //replay.setSize(replay.getWidth()*0.7f, replay.getHeight()*0.7f);
        replay.setPosition(getWidth()-replay.getWidth(),0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        failFont.getData().setScale(0.65f);
        failFont.draw(batch, "You Have Failed Level " + level,
                stage.getWidth()/2 - 130*scale,stage.getHeight()*0.5f);

        failFont.getData().setScale(1);
        failFont.draw(batch, "Score: " + score,
                stage.getWidth()/2 - 80*scale,stage.getHeight()*0.4f);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        bg.setVisible(visible);
    }
    private void hideDialog() {
        replay.clearListeners();
        setVisible(false);
        GameManager.getInstance().onGameResume();
    }
    public void showDialog(Runnable replayBtnEvent, int score, int level) {
        this.score = score;
        this.level = level;
        replay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                replayBtnEvent.run();
                hideDialog();
            }
        });
        setVisible(true);
        GameManager.getInstance().onGamePaused();
        BackGround.instance.toBack();
        bg.toFront();
        toFront();
    }

    private Texture createBlackTexture() {
        Pixmap pixmap = new Pixmap((int) stage.getWidth(), (int) stage.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0,0,0,0.4f));

        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }
}
