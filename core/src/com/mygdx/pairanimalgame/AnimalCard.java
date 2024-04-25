package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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
        toFront();
        return this;
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

    public void setActive(boolean active) {
        isActive = active;
    }
}
