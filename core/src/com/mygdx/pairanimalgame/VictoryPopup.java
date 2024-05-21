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

public class VictoryPopup extends Group {
    private final Image bg;
    private final TextureRegion victory;
    private final MyButton nextBtn;
    private final float scale;
    private final Stage stage;
    private BitmapFont congratFont;
    private BitmapFont titleFont;
    private BitmapFont valueFont;
    private int score;
    public VictoryPopup(Stage stage, AssetManager assetManager) {
        this.victory = assetManager.get("texture/play.atlas", TextureAtlas.class)
                .findRegion("victory_en");
        this.nextBtn = new MyButton(assetManager.get("texture/common.atlas", TextureAtlas.class)
                .findRegion("btn_yellow")).setText("Next");
        this.stage = stage;
        this.bg = new Image(createBlackTexture());
        this.congratFont = ConnectAnimalGame.getInstance().getAssetMN().get("font/comic.fnt");
        this.titleFont = ConnectAnimalGame.getInstance().getAssetMN().get("font/comic.fnt");

        scale = stage.getWidth()*0.4f / victory.getRegionWidth();
        float width = stage.getWidth()*0.4f;
        float height = stage.getHeight()*0.8f;

        setBounds(stage.getWidth()/2-width/2, stage.getHeight()/2 - height/2, width, height);
        setStage(stage);
        setVisible(false);

        initialize();
    }

    private void initialize() {
        stage.addActor(bg);

        Image vi = new Image(victory);
        vi.setSize(vi.getWidth()*scale, vi.getHeight()*scale);
        vi.setPosition(getWidth()/2-vi.getWidth()/2,getHeight()-vi.getHeight());
        addActor(vi);

/*        MyLabel con = new MyLabel("Congratulation! you have passed level 1 (high templar)");
        con.setPosition(0, vi.getY()-vi.getHeight()-con.getFont().getData().lineHeight - 50);
        addActor(con);*/

        addActor(nextBtn);
        nextBtn.setPosition(getWidth()/2-nextBtn.getWidth()/2,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        congratFont.getData().setScale(0.65f);
        congratFont.setColor(Color.GREEN);
        congratFont.draw(batch, "Congratulation! you have passed level 1 (high templar)",
                stage.getWidth()/2 - getWidth()/2,stage.getHeight()*0.65f);

        congratFont.getData().setScale(0.85f);
        congratFont.setColor(Color.WHITE);
        titleFont.draw(batch, "Time Remain: " + TimerBar.timeLeft,
                stage.getWidth()/2 - getWidth()/2,stage.getHeight()*0.55f);
        titleFont.draw(batch, "MaxCombo: " + ComboBar.getInstance().getMaxCombo(),
                stage.getWidth()/2 - getWidth()/2,stage.getHeight()*0.45f);
        titleFont.draw(batch, "Score: " + score,
                stage.getWidth()/2 - getWidth()/2,stage.getHeight()*0.35f);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        bg.setVisible(visible);
    }
    public void hideDialog() {
        nextBtn.clearListeners();
        setVisible(false);
        GameManager.getInstance().onGameResume();
    }
    public void showDialog(Runnable nextBtnEvent, int score) {
        this.score = score;
        nextBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextBtnEvent.run();
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
