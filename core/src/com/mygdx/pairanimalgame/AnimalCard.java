package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AnimalCard extends Group {
    static int width = 68 - 15;
    static int height = 80 - 15;
    static int marginLeft = 100;
    static int marginBottom = 100;
    private final Image border;
    private final int type;
    private boolean selected;
    private int indexX;
    private int indexY;
    private boolean isActive = true;
    private boolean isActionActive = false;

    public AnimalCard(Image cucxilau, Image animal, int type,
                      Image border, int indexX, int indexY) {
        this.border = border;
        this.indexX = indexX;
        this.indexY = indexY;
        this.type = type;

        animal.setPosition(8, 21);

        this.addActor(cucxilau);
        this.addActor(animal);
        this.addActor(border);

        border.setVisible(false);

        setBounds(cucxilau.getX(), cucxilau.getY(),
                cucxilau.getImageWidth(), cucxilau.getImageHeight());
        setPosition(AnimalCard.width*indexY + marginLeft,
                GameScreen.height - AnimalCard.height*indexX - marginBottom - AnimalCard.height);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //setIndex(0,0,false);
            }
        });
    }

    public AnimalCard(TextureRegion cucxilau, TextureRegion an, int type, TextureRegion selected, int row, int col) {
        this(new Image(cucxilau), new Image(an), type, new Image(selected), row, col);
    }
    public int getType() {
        return type;
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }
    public AnimalCard setIndex(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;

        setPosition(AnimalCard.width*indexY + marginLeft,
                GameScreen.height - AnimalCard.height*indexX - marginBottom - AnimalCard.height);
        return this;
    }

    public void setIndex(int indexX, int indexY, boolean isActionActive) {
        if(indexY == this.indexY && indexX == this.indexX) return;

        this.indexX = indexX;
        this.indexY = indexY;
        this.isActionActive = true;

        float posX = AnimalCard.width*indexY + marginLeft;
        float posY = GameScreen.height - AnimalCard.height*indexX - marginBottom - AnimalCard.height;

        if(isActive) createAction(posX, posY);
    }

    private void createAction(float posX, float posY) {
        // Tạo một Action di chuyển
        MoveToAction moveAction = Actions.action(MoveToAction.class);
        moveAction.setPosition(posX, posY);
        moveAction.setDuration(0.5f);
        moveAction.setInterpolation(Interpolation.sineIn);

        // Xóa action sau khi hoàn thành
        Action onComplete = Actions.run(() -> {
            removeAction(moveAction);
            this.isActionActive = false;
        });

        addAction(Actions.sequence(moveAction, onComplete));
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
        border.setVisible(selected);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {isActive = active;}

    public void setInActive(Stage stage, EffectMN effectMN) {
        isActive = false;
        stage.addActor(effectMN.getFireworkEffectActor(this));
    }

    public boolean isActionActive() {
        return isActionActive;
    }
}
