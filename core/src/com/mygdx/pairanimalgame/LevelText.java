package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LevelText extends Actor {
    static String text;
    private BitmapFont font;
    private Color fontColor;

    public LevelText(String text) {
        LevelText.text = text;
        this.font = new BitmapFont();
        this.fontColor = Color.WHITE;
        this.font.getData().setScale(1.0f);
    }
    public LevelText(String text, BitmapFont font, Color fontColor) {
        this(text);
        this.font = font;
        this.fontColor = fontColor;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.setColor(fontColor.r, fontColor.g, fontColor.b, fontColor.a * parentAlpha);
        font.draw(batch, "Level: " + text, getX(), getY());
    }
}
