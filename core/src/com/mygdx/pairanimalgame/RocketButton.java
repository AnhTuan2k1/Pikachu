package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RocketButton extends MyButton{
    public RocketButton(TextureAtlas atlas) {
        super(atlas.findRegion("btn_rocket"), atlas.findRegion("btn_pause"));
    }
}
