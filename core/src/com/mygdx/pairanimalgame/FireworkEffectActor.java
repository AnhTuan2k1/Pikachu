package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class FireworkEffectActor extends Effect {
    public FireworkEffectActor(PooledEffect pooledEffect, AnimalCard animalCard) {
        super(pooledEffect, animalCard);
        animalCard.setActive(false);
    }

    public FireworkEffectActor(PooledEffect pooledEffect) {
        super(pooledEffect);
    }

    static void createFirework(Stage stage, ParticleEffectPool particleEffectPool, AnimalCard animalCard) {
        ParticleEffectPool.PooledEffect pooledEffect = particleEffectPool.obtain();
        FireworkEffectActor fireworkEffectActor = new FireworkEffectActor(pooledEffect, animalCard);
        stage.addActor(fireworkEffectActor);
    }
    static void createFirework(Stage stage, ParticleEffectPool particleEffectPool) {
        ParticleEffectPool.PooledEffect pooledEffect = particleEffectPool.obtain();
        FireworkEffectActor fireworkEffectActor = new FireworkEffectActor(pooledEffect);
        stage.addActor(fireworkEffectActor);
    }
}
