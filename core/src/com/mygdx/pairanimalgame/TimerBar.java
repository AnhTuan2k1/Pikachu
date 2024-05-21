package com.mygdx.pairanimalgame;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class TimerBar extends Actor{
    private final TextureRegion bar;
    private final TextureRegion frame;
    private final AnimalMatrix animalMatrix;
    static float timeMax;
    static float timeLeft;

    public TimerBar(Stage stage, TextureRegion frame, TextureRegion bar, float timeLeft, AnimalMatrix animalMatrix) {
        this.frame = frame;
        this.bar = bar;
        this.animalMatrix = animalMatrix;
        TimerBar.timeLeft = timeLeft;

        setSize(bar.getRegionWidth(), bar.getRegionHeight());

        setPosition((float) stage.getWidth() /2 - (float) frame.getRegionWidth() /2,
                stage.getHeight() - frame.getRegionHeight() - 10);
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
                animalMatrix.defeat();
            }
            if(animalMatrix!=null&&animalMatrix.remainSeconds > timeLeft + 1){
                animalMatrix.remainSeconds--;
            }
        }
    }

   @Override
    public void draw(Batch batch, float parentAlpha) {
       if (timeLeft > 0) {
           //float width = getWidth() * (timeLeft / timeMax);
           float srcWidth = bar.getRegionWidth() * (timeLeft / timeMax);

           batch.draw(bar.getTexture(), getX(), getY(), bar.getRegionX(), bar.getRegionY(), (int) srcWidth, (int) getHeight());
       }
       else timeLeft = 3;

       batch.draw(frame, getX() - 5, getY() - 5);
    }
}

