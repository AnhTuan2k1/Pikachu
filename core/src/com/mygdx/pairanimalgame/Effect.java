package com.mygdx.pairanimalgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Effect extends Actor {
    protected ParticleEffectPool.PooledEffect effect;
    public Effect(ParticleEffectPool.PooledEffect effect) {
        this.effect = effect;
        this.effect.start();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.update(delta);

        if (effect.isComplete()) {
            effect.free(); // Giải phóng hiệu ứng lại vào pool
            remove();  // Xóa actor khỏi stage sau khi hiệu ứng hoàn thành
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        effect.draw(batch);
    }
}
