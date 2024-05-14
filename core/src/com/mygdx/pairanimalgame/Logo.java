package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Logo extends Actor {
    private final TextureRegion textureRegion;

    public Logo(Stage stage, TextureRegion textureRegion) {
        this.textureRegion = textureRegion;

        float scale = stage.getWidth()*0.4f / textureRegion.getRegionWidth();
        float height = textureRegion.getRegionHeight() * scale;
        float width = textureRegion.getRegionWidth() * scale;
        setBounds(stage.getWidth()/2 - width/2,
                stage.getHeight() - height - 20,
                width, height);

        setOrigin(getWidth() / 2, getHeight() / 2);
        addContinuousScalingAction();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }

    private void addContinuousScalingAction() {
        addAction(
                Actions.forever(
                Actions.sequence(
                        Actions.scaleTo(0.9f, 1.1f, 2),
                        Actions.scaleTo(1.1f, 0.9f, 2)
                )
        ));
    }
}
