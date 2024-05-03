package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PauseButton extends ImageButton {
    public PauseButton(TextureAtlas atlas) {
        super(createButtonStyle(atlas));
        setPosition(Gdx.graphics.getWidth() - 70, Gdx.graphics.getHeight() - 70);
    }
    private static ImageButtonStyle createButtonStyle(TextureAtlas atlas) {
        Drawable upDrawable = new TextureRegionDrawable(atlas.findRegion("btn_pause"));
        Drawable downDrawable = new TextureRegionDrawable(atlas.findRegion("hammer"));

        ImageButtonStyle style = new ImageButtonStyle();
        style.up = upDrawable;        // Thiết lập hình ảnh khi nút ở trạng thái bình thường
        style.down = downDrawable;    // Thiết lập hình ảnh khi nút được nhấn
        return style;
    }
}
