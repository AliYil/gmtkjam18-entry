package com.aliyil.gmtkjam18;

import com.aliyil.gmtkjam18.entity.Entity;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class PooledEntityEffect {
    ParticleEffectPool.PooledEffect effect;
    Entity parentEntity;
    boolean respectPause = false;

    public PooledEntityEffect(ParticleEffectPool.PooledEffect effect) {
        this.effect = effect;
    }
}
