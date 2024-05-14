package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ToggleButton extends Actor {
    private final TextureRegion textureON;
    private final TextureRegion textureOFF;
    private boolean isOn = true;

    public ToggleButton(TextureRegion textureON, TextureRegion textureOFF) {
        this.textureON = textureON;
        this.textureOFF = textureOFF;
        // Thiết lập kích thước mặc định cho actor này
        setBounds(0, 0,
                textureON.getRegionWidth()*0.65f,
                textureON.getRegionHeight()*0.65f);

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isOn = !isOn;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion currentTexture = isOn ? textureON : textureOFF;
        batch.draw(currentTexture, getX(), getY(), getWidth(), getHeight());
    }
}
