package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class LazeEffectActor extends Effect{
    public LazeEffectActor(ParticleEffectPool.PooledEffect pooledEffect) {
        super(pooledEffect);
    }
    public LazeEffectActor(ParticleEffectPool.PooledEffect pooledEffect, Path.Point point) {
        super(pooledEffect);

        float posX = AnimalCard.getPosX(point.y) + AnimalCard.width*AnimalCard.getAnimalScale()/2;
        float posY = AnimalCard.getPosY(point.x) + AnimalCard.height*AnimalCard.getAnimalScale()/2;

        setPosition(posX, posY);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        toFront();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void positionChanged() {
        effect.setPosition(getX(), getY());
    }
}
