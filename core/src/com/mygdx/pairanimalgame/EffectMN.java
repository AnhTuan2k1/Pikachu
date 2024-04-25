package com.mygdx.pairanimalgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class EffectMN {
    private ParticleEffectPool fireworkEffectPool;
    private ParticleEffectPool lazeEffectPool;

    public EffectMN(AssetManager assetMN) {
        ParticleEffect fireworkEffect = assetMN.get("effect/firework.p", ParticleEffect.class);
        fireworkEffectPool = new ParticleEffectPool(fireworkEffect, 2, 8);

        ParticleEffect lazeEffect = assetMN.get("effect/laze.p", ParticleEffect.class);
        lazeEffectPool = new ParticleEffectPool(lazeEffect, 3, 12);
    }

    public FireworkEffectActor getFireworkEffectActor() {
        return new FireworkEffectActor(fireworkEffectPool.obtain());
    }

    public FireworkEffectActor getFireworkEffectActor(AnimalCard animalCard) {
        return new FireworkEffectActor(fireworkEffectPool.obtain(), animalCard);
    }

    public void dispose(){
        fireworkEffectPool.clear();
        fireworkEffectPool.clear();
    }

    public ParticleEffectPool.PooledEffect getLazeEffect() {
        return lazeEffectPool.obtain();
    }
}
