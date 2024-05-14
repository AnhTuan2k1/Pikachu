package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MyLabel extends Actor {
    private String text;
    private BitmapFont font;
    private Color fontColor;

    public MyLabel(String text) {
        this.text = text;
        this.font = new BitmapFont();
        //this.font = ConnectAnimalGame.getInstance().getAssetMN().get("font/comic.fnt");
        //this.font.getData().setScale(0.5f);
        this.fontColor = Color.WHITE;
        //setBounds(0,0,300,-20);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }
    public MyLabel(String text, Color fontColor) {
        this(text);
        this.fontColor = fontColor;
    }

    public MyLabel(String text, float scale) {
        this(text);
        this.font.getData().setScale(scale);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.setColor(fontColor.r, fontColor.g, fontColor.b, fontColor.a * parentAlpha);
        font.draw(batch, text, getX(), getY());
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        // Thay đổi vị trí xử lý, nếu cần
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }
    public BitmapFont getFont(){
        return this.font;
    }

    public void setFontColor(Color color) {
        this.fontColor = color;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Cập nhật logic nếu cần
    }
}
