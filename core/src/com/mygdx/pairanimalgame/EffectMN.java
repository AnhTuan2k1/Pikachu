package com.mygdx.pairanimalgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EffectMN {
    private ParticleEffectPool fireworkEffectPool;
    private ParticleEffectPool lazeVerticalEffectPool;
    private ParticleEffectPool lazeHorizontalEffectPool;

    public EffectMN(AssetManager assetMN) {
        ParticleEffect fireworkEffect = assetMN.get("effect/firework.p", ParticleEffect.class);
        fireworkEffectPool = new ParticleEffectPool(fireworkEffect, 2, 8);

        ParticleEffect lazeEffect = assetMN.get("effect/laze.p", ParticleEffect.class);
        lazeVerticalEffectPool = new ParticleEffectPool(lazeEffect, 5, 20);

        lazeEffect = assetMN.get("effect/laze2.p", ParticleEffect.class);
        lazeHorizontalEffectPool = new ParticleEffectPool(lazeEffect, 5, 20);
    }
    public Actor getLazeVerticalEffectActor(Path.Point point) {
        return new LazeEffectActor(lazeVerticalEffectPool.obtain(), point);
    }

    public Actor getLazeHorizontalEffectActor(Path.Point point) {
        return new LazeEffectActor(lazeHorizontalEffectPool.obtain(), point);
    }
    public FireworkEffectActor getFireworkEffectActor(AnimalCard animalCard) {
        return new FireworkEffectActor(fireworkEffectPool.obtain(), animalCard);
    }

    public void dispose(){
        fireworkEffectPool.clear();
        lazeVerticalEffectPool.clear();
        lazeHorizontalEffectPool.clear();
    }
}
