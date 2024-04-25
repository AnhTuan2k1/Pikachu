package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Effect extends Actor {
    private ParticleEffectPool.PooledEffect effect;
    private float elapsedTime = 0; // Thời gian đã trôi qua
    private AnimalCard animalCard;
    public Effect(ParticleEffectPool.PooledEffect effect) {
        this.effect = effect;
        this.effect.start();
    }

    public Effect(ParticleEffectPool.PooledEffect effect, AnimalCard animalCard) {
        this(effect);
        this.animalCard = animalCard;
        setPosition(animalCard.getX() + (float) AnimalCard.width /2 + 2,
                animalCard.getY() + (float) AnimalCard.height /2 + 15);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.update(delta);
        elapsedTime += delta; // Cập nhật thời gian đã trôi qua

        if (effect.isComplete()) {
            if(animalCard != null) animalCard.setVisible(false);
            effect.free(); // Giải phóng hiệu ứng lại vào pool
            remove();  // Xóa actor khỏi stage sau khi hiệu ứng hoàn thành
        }

/*        if(elapsedTime >= 2) {
            effect.free();
            remove();
        }*/
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        effect.draw(batch);
    }

    @Override
    protected void positionChanged() {
        effect.setPosition(getX(), getY());
    }
}
