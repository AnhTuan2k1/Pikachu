package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class FireworkEffectActor extends Effect {
    private AnimalCard animalCard;
    private float elapsedTime = 0; // Thời gian đã trôi qua
    public FireworkEffectActor(PooledEffect pooledEffect, AnimalCard animalCard) {
        super(pooledEffect);
        this.animalCard = animalCard;
        animalCard.setActive(false);
        setPosition(animalCard.getX() + (float) AnimalCard.width*AnimalCard.getAnimalScale() /2 + 2,
                animalCard.getY() + (float) AnimalCard.height*AnimalCard.getAnimalScale() /2 + 15);

    }
    public FireworkEffectActor(PooledEffect pooledEffect) {
        super(pooledEffect);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta; // Cập nhật thời gian đã trôi qua
        toFront();

        if(elapsedTime >= 0.1f) {
            if(animalCard != null) animalCard.setVisible(false);
        }
    }

    @Override
    protected void positionChanged() {
        effect.setPosition(getX(), getY());
    }
}
