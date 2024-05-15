package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ComboBar extends Group {
    private final Texture bar;
    private float timeLeft;
    private int comboStep;
    private final MyLabel label;
    private final MyLabel effectLabel;
    static private ComboBar instance;
    static public ComboBar getInstance(){
        if(instance == null) return new ComboBar();
        else return instance;
    }
    private ComboBar() {
        this.bar = createWhiteTexture();
        this.timeLeft = 0;
        this.comboStep = 0;
        this.label = new MyLabel("");
        this.effectLabel = new MyLabel("");

        setBounds(950, 666,200, 7);
        ComboBar.instance = this;

        label.setPosition(50, 22);
        effectLabel.setPosition(50, 22);

        addActor(label);
        addActor(effectLabel);
    }

    @Override
    public void act(float delta) {
        if(!GameManager.getInstance().isPaused()){
            super.act(delta);

            // Cập nhật thời gian còn lại
            timeLeft -= delta;
            timeLeft = Math.max(timeLeft, 0); // Đảm bảo thời gian không đi xuống âm

            if (timeLeft == 0) {
                // Thực hiện hành động khi thời gian hết
                comboStep = 0;
                setVisible(false);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (timeLeft > 0) {
            batch.draw(bar, getX(), getY(), getWidth()*timeLeft/2, getHeight());
        }
    }

    private Texture createWhiteTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
    public void nextCombo(){
        this.timeLeft = 2;
        comboStep++;
        label.setText("COMBO " + comboStep +" (+" + comboStep*20 + " Score)");
        effectLabel.setText("COMBO " + comboStep +" (+" + comboStep*20 + " Score)");
        addEffectLabelAction();
        setVisible(true);
    }

    private void addEffectLabelAction() {
        effectLabel.clearActions();
        effectLabel.addAction(
                Actions.sequence(
                        Actions.scaleTo(1.5f,1.5f,0.4f, Interpolation.sineOut),
                        Actions.run(()->effectLabel.setScale(1))
                )

        );
        effectLabel.addAction(
                Actions.sequence(
                        Actions.fadeIn(0.7f),
                        Actions.fadeOut(0.001f)
                )

        );
        effectLabel.addAction(
                Actions.sequence(
                        Actions.moveTo(0, effectLabel.getY()*1.5f, 0.4f, Interpolation.sineOut),
                        Actions.run(()->effectLabel.setPosition(50, effectLabel.getY()/1.5f))
                )

        );
    }

    public int getCombo(){
        return comboStep;
    }
}