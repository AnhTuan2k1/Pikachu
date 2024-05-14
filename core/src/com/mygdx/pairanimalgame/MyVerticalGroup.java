package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class MyVerticalGroup extends VerticalGroup {

    static final float marginTop = 130;
    public MyVerticalGroup(Stage stage) {
        space(10); // set the spacing between buttons
        pad(10); // set padding around the group
        fill(); // make all items in the group expand to fill the width
        center(); // align items to the center horizontally
        setPosition(40, stage.getHeight() - 120 - AnimalMatrix.marginTop  /*- AnimalCard.marginBottom*/);
    }
}
