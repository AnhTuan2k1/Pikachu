package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RandomButton extends MyButton{
    public RandomButton(TextureAtlas atlas) {
        super(atlas.findRegion("btn_random"), atlas.findRegion("btn_pause"));
    }
}
