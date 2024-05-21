package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BackGround extends Actor implements IGameObserver{
    private final TextureRegion textureRegion;
    public static BackGround instance;
    public BackGround(Stage stage, TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        setBounds(0, 0, stage.getWidth(), stage.getHeight());

        GameManager.getInstance().registerObserver(this);
        BackGround.instance = this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);  // Áp dụng màu và độ trong suốt
        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public boolean remove() {
        GameManager.getInstance().unregisterObserver(this);
        return super.remove();
    }

    @Override
    public void OnGamePaused(boolean isPaused) {
        if(isPaused) toFront();
        else toBack();
    }
}

