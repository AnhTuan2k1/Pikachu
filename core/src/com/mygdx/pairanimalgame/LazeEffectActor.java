package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LazeEffectActor extends Effect{
    public LazeEffectActor(ParticleEffectPool.PooledEffect pooledEffect, AnimalCard animalCard) {
        super(pooledEffect, animalCard);
    }

    public LazeEffectActor(ParticleEffectPool.PooledEffect pooledEffect) {
        super(pooledEffect);
    }

    static void createLaze(Stage stage, ParticleEffectPool particleEffectPool, AnimalCard animalCard) {
        ParticleEffectPool.PooledEffect pooledEffect = particleEffectPool.obtain();
        LazeEffectActor fireworEffect = new LazeEffectActor(pooledEffect, animalCard);
        stage.addActor(fireworEffect);
    }
    static void createLaze(Stage stage, ParticleEffectPool particleEffectPool) {
        ParticleEffectPool.PooledEffect pooledEffect = particleEffectPool.obtain();
        LazeEffectActor fireworEffect = new LazeEffectActor(pooledEffect);
        stage.addActor(fireworEffect);
    }
}
