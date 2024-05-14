package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Score extends Actor {
    static String text;
    private BitmapFont font;
    private Color fontColor;

    public Score() {
        this.font = new BitmapFont();
        this.fontColor = Color.WHITE;
        this.font.getData().setScale(1.0f);
    }
    public Score(String text) {
        this();
        Score.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.setColor(fontColor.r, fontColor.g, fontColor.b, fontColor.a * parentAlpha);
        font.draw(batch, Score.text, getX(), getY());
    }
    public void setFontColor(Color color) {
        this.fontColor = color;
    }

}
