package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimalCard extends Group {
    static int width = 68 - 10;
    static int height = 80 - 10;
    static int marginLeft = 100;
    static int marginBottom = 100;
    private final Image border;
    private final int id;
    private boolean selected;
    private int indexX;
    private int indexY;

    public AnimalCard(Image cucxilau, Image animal, int id,
                      Image border, int indexX, int indexY) {
        this.border = border;
        this.indexX = indexX;
        this.indexY = indexY;
        this.id = id;

        animal.setPosition(8, 21);

        this.addActor(cucxilau);
        this.addActor(animal);
        this.addActor(border);

        border.setVisible(false);

        setBounds(cucxilau.getX(), cucxilau.getY(),
                cucxilau.getImageWidth(), cucxilau.getImageHeight());
        setPosition(AnimalCard.width*indexX + marginLeft,
                GameScreen.height - AnimalCard.height*indexY - marginBottom - AnimalCard.height);
    }

    public AnimalCard(TextureRegion cucxilau, TextureRegion an, int id, TextureRegion selected, int row, int col) {
        this(new Image(cucxilau), new Image(an), id, new Image(selected), row, col);
    }

    public int getId() {
        return id;
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

        setPosition(AnimalCard.width*indexX + marginLeft,
                GameScreen.height - AnimalCard.height*indexY - marginBottom - AnimalCard.height);
        return this;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
        border.setVisible(selected);
    }

}
