package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HintButton extends MyButton{
    public HintButton(TextureAtlas atlas) {
        super(atlas.findRegion("btn_hint"), atlas.findRegion("btn_pause"));
    }
}
