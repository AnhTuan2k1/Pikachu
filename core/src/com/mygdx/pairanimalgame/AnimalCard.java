package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AnimalCard extends Group{
    private static float animalScale = 1;
    static float animalScaleY = 1;
    static final float width = 68 - 15;
    static final float height = 80 - 15;
    static float marginLeft = 100;
    static float marginBottom = 150;
    static final float animalPosWith = 8;
    static final float animalPosHeight = 21;
    private final Image border;
    private final Image cucxilau;
    private final Image animal;
    private final int type;
    private boolean selected;
    private int indexX;
    private int indexY;
    private boolean isActive = true;
    private boolean isActionActive = false;

    private AnimalCard(Image cucxilau, Image animal, int type,
                      Image border, int indexX, int indexY) {
        this.border = border;
        this.animal = animal;
        this.cucxilau = cucxilau;
        this.indexX = indexX;
        this.indexY = indexY;
        this.type = type;

        animal.setPosition(animalPosWith*getAnimalScale(), animalPosHeight*getAnimalScale());

        this.addActor(cucxilau);
        this.addActor(animal);
        this.addActor(border);

        border.setVisible(false);

        //setBounds(cucxilau.getX(), cucxilau.getY(),
                //cucxilau.getImageWidth(), cucxilau.getImageHeight());

        setPosition(getPosX(indexY), getPosY(indexX));
        setSize(AnimalCard.width, AnimalCard.height);
        setOrigin(getWidth()/2, getHeight()/2);
    }

    public AnimalCard(TextureRegion cucxilau, TextureRegion an, int type, TextureRegion selected, int row, int col) {
        this(new Image(cucxilau), new Image(an), type, new Image(selected), row, col);

        if(AnimalCard.getAnimalScale() != 1){
            this.cucxilau.setScale(AnimalCard.getAnimalScale());
            this.animal.setScale(AnimalCard.getAnimalScale());
            this.border.setScale(AnimalCard.getAnimalScale());
            //setScale(AnimalCard.getAnimalScale());
        }

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
    public static float getAnimalScale() {
        return animalScale;
    }
    public static void setAnimalScale(float animalScale) {
        AnimalCard.animalScale = animalScale;
    }

    public AnimalCard setIndex(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;

        setPosition(getPosX(indexY), getPosY(indexX));
        return this;
    }

    public void setIndex(int indexX, int indexY, boolean isActionActive) {
        if(indexY == this.indexY && indexX == this.indexX) return;

        this.indexX = indexX;
        this.indexY = indexY;
        this.isActionActive = true;

        //setPosition(posX, posY);
        if(isActive) createAction(getPosX(indexY), getPosY(indexX));
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

        stage.addActor(new PathActor(Pikachu.foundPath));

        animal.setOrigin(getWidth()/2, getHeight()/2);
        animal.addAction(Actions.fadeIn(0.1f, Interpolation.sineIn));
        animal.addAction(
                Actions.sequence(
                        Actions.scaleTo(3f, 3f, 0.1f, Interpolation.sineIn),
                        Actions.run(()->animal.setScale(1))
                )
        );
    }

    public boolean isActionActive() {
        return isActionActive;
    }

    public static float getPosX(int indexY){
        return AnimalCard.width*getAnimalScale()*indexY + marginLeft;
    }
    public static float getPosY(int indexX){
        return GameScreen.getInstance().stage.getHeight()
                - AnimalCard.height*getAnimalScale()*(indexX+1)
                - AnimalMatrix.marginTop
                - marginBottom/2;
    }
}
