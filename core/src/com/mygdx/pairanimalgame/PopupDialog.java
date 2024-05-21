package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import javax.swing.GroupLayout;

public class PopupDialog extends Group {
    private final TextureRegion panel;
    private final TextureRegion title;
    private final Stage stage;
    private final float scale;
    private final MyButton rightBtn;
    private final MyButton leftBtn;
    private final MyButton cancelBtn;
    private Table horizontalTable;
    private MyLabel label;

    private PopupDialog(Stage stage, TextureAtlas commonAtlas) {
        this.panel = commonAtlas.findRegion("panel");
        this.title = commonAtlas.findRegion("tittle_pausing");
        this.stage = stage;
        this.leftBtn = new MyButton(commonAtlas.findRegion("btn_red"));
        this.rightBtn = new MyButton(commonAtlas.findRegion("btn_yellow"));
        this.cancelBtn = new MyButton(commonAtlas.findRegion("btn_exit"));

        scale = stage.getWidth()*0.6f / title.getRegionWidth();
        float height = ((float) title.getRegionHeight() /2 + panel.getRegionHeight()) * scale;
        float width = title.getRegionWidth() * scale;

        setBounds((stage.getWidth() - width)/2, (stage.getHeight() - height)/2, width, height);
        initialize();
    }


    public PopupDialog(Stage stage, TextureAtlas commonAtlas, MyButton rightBtn, MyButton leftBtn, Table content) {
        this.panel = commonAtlas.findRegion("panel");
        this.title = commonAtlas.findRegion("tittle_pausing");
        this.stage = stage;
        this.leftBtn = leftBtn;
        this.rightBtn = rightBtn;
        this.cancelBtn = new MyButton(commonAtlas.findRegion("btn_exit"));
        this.horizontalTable = content;

        scale = stage.getWidth()*0.6f / title.getRegionWidth();
        float height = ((float) title.getRegionHeight() /2 + panel.getRegionHeight()) * scale;
        float width = title.getRegionWidth() * scale;

        setBounds((stage.getWidth() - width)/2, (stage.getHeight() - height)/2, width, height);
        setOrigin(width/2, height/2);
        initialize();
    }

    public PopupDialog(Stage stage, TextureAtlas commonAtlas, MyButton rightBtn, MyButton leftBtn, String text) {
        this.panel = commonAtlas.findRegion("panel");
        this.title = commonAtlas.findRegion("tittle_notice");
        this.stage = stage;
        this.leftBtn = leftBtn;
        this.rightBtn = rightBtn;
        this.cancelBtn = new MyButton(commonAtlas.findRegion("btn_exit"));
        this.label = new MyLabel(text, 2);

        scale = stage.getWidth()*0.6f / title.getRegionWidth();
        float height = ((float) title.getRegionHeight() /2 + panel.getRegionHeight()) * scale;
        float width = title.getRegionWidth() * scale;

        setBounds((stage.getWidth() - width)/2, (stage.getHeight() - height)/2, width, height);
        setOrigin(width/2, height/2);
        initialize();
    }

    private void initialize() {
        System.out.println("PopupDialog initialize");

        Image panelImage = new Image(panel);
        panelImage.setSize(panel.getRegionWidth()*scale, panel.getRegionHeight()*scale);
        panelImage.setPosition(getWidth()/2-panelImage.getWidth()/2, 0);
        addActor(panelImage);

        Image titleImage = new Image(title);
        titleImage.setSize(title.getRegionWidth()*scale, title.getRegionHeight()*scale);
        titleImage.setPosition(getWidth()/2-titleImage.getWidth()/2, getHeight() - titleImage.getHeight());
        titleImage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (ConnectAnimalGame.getInstance().getAdsController().isInterstitialAdLoaded()) {
                    ConnectAnimalGame.getInstance().getAdsController().showInterstitialAd();
                }
            }
        });
        addActor(titleImage);

        leftBtn.setSize(leftBtn.getWidth()*scale*0.9f, leftBtn.getHeight()*scale*0.9f);
        leftBtn.setPosition(getWidth()/2-leftBtn.getWidth()-20, 50);
        addActor(leftBtn);

        rightBtn.setSize(rightBtn.getWidth()*scale*0.9f, rightBtn.getHeight()*scale*0.9f);
        rightBtn.setPosition(getWidth()/2+20, 50);
        addActor(rightBtn);

        cancelBtn.setSize(90,90);
        cancelBtn.setPosition(getWidth()-20, getHeight(), Align.topRight);
        cancelBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideDialog();
            }
        });
        addActor(cancelBtn);

        if(label != null)
        {
            label.setPosition(150, getHeight()/2);
            addActor(label);
        }
        if(horizontalTable != null)
        {
            horizontalTable.getCells().first().width(panel.getRegionWidth()*scale - 50);
            addActor(horizontalTable);
        }

        setVisible(false); // Ẩn dialog khi khởi tạo
    }

    public void showDialog() {
        setVisible(true);
        GameManager.getInstance().onGamePaused();
        toFront();

        setScale(0.2f);
        addAction(Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.4f, Interpolation.sineOut),
                Actions.scaleTo(1,1,0.2f)
        ));
    }
    public void showDialog(Runnable funLeftbtn, Runnable funRightbtn) {
        showDialog();
        leftBtn.clearListeners();
        leftBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideDialog(funLeftbtn);
            }
        });
        rightBtn.clearListeners();
        rightBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hideDialog(funRightbtn);
            }
        });
    }
    public void hideDialog() {
        addAction(Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.15f, Interpolation.sineOut),
                Actions.scaleTo(0.2f,0.2f,0.3f),
                Actions.run(() -> {
                    setScale(1);
                    GameManager.getInstance().onGameResume();
                    setVisible(false);
                })
        ));
    }
    public void hideDialog(Runnable function) {
        addAction(Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.15f, Interpolation.sineOut),
                Actions.scaleTo(0.2f,0.2f,0.2f),
                Actions.run(() -> {
                    setScale(1);
                    GameManager.getInstance().onGameResume();
                    setVisible(false);
                    function.run();
                })
        ));
    }

    public void setTextlabel(String text){
        label.setText(text);
    }
}

