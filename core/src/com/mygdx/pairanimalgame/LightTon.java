package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LightTon extends Actor {
    private final TextureRegion textureRegion1;
    private final TextureRegion textureRegion2;

    public LightTon(Stage stage, TextureRegion textureRegion1, TextureRegion textureRegion2, float offset) {
        this.textureRegion1 = textureRegion1;
        this.textureRegion2 = textureRegion2;

        float scale = stage.getWidth()*0.1f / textureRegion1.getRegionWidth();
        float height = textureRegion1.getRegionHeight() * scale;
        float width = textureRegion1.getRegionWidth() * scale;

        setBounds(stage.getWidth()/2 - width/2 + offset,
                stage.getHeight() - height,
                width, height);

        setOrigin(getWidth()/2, getHeight());
        addContinuousRotatingAction();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureRegion1, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }


    private void addContinuousRotatingAction() {
        float rotateAmount = -15; // Độ xoay cho mỗi hướng, đơn vị là độ
        float duration = 2;

        addAction(Actions.forever(
                Actions.sequence(
                        Actions.rotateBy(rotateAmount, duration),
                        Actions.rotateBy(-rotateAmount * 2, duration * 2),
                        Actions.rotateBy(rotateAmount, duration)
                )
        ));
    }
}
