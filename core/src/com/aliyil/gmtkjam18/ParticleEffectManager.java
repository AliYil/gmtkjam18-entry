package com.aliyil.gmtkjam18;

import com.aliyil.gmtkjam18.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.utils.Array;

public class ParticleEffectManager {

    private Array<PooledEntityEffect> effects;
    private Game gameInstance;

    public ParticleEffectManager(Game gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void renderAllParticles(Batch batch) {
        for (PooledEntityEffect entityEffect : effects) {
            if (entityEffect.parentEntity != null) {
                if (!entityEffect.parentEntity.isRunning()) {
                    entityEffect.effect.getEmitters().first().setContinuous(false);
                } else {
                    entityEffect.effect.setPosition(entityEffect.parentEntity.getX(), entityEffect.parentEntity.getY());
                }
            }
            entityEffect.effect.draw(batch, entityEffect.respectPause ? gameInstance.sharedValues.paused ? 0 : S.dts(gameInstance.sharedValues.gameSpeed) : S.dts(gameInstance.sharedValues.gameSpeed));

            if (!entityEffect.effect.getEmitters().first().isContinuous() && entityEffect.effect.isComplete()) {
                effects.removeValue(entityEffect, true);
                entityEffect.effect.free();
            }
        }
    }

    public void loadResources() {
        effects = new Array<PooledEntityEffect>();

//        ParticleEffect pBigExplosion = new ParticleEffect();
//        pBigExplosion.load(Gdx.files.internal("particles/big_explosion.p"), Gdx.files.internal("textures"));
//        pBigExplosion.start();
//        pBigExplosion.scaleEffect(1f);
//        ppBigExplosion = new ParticleEffectPool(pBigExplosion, 20, 100);
    }

    public void releaseResources() {
    }

    public int DBG_getTotalParticles() {
        int returnValue = 0;
        for (PooledEntityEffect entityEffect : effects) {
            for (ParticleEmitter emitter : entityEffect.effect.getEmitters()) {
                returnValue += emitter.getActiveCount();
            }
        }
        return returnValue;
    }

    //Should call setCountinuous(true) for all continuous particles

//    public void newBigExplosion(float x, float y) {
//        ParticleEffectPool.PooledEffect explosion = ppBigExplosion.obtain();
//        explosion.setPosition(x, y);
//        PooledEntityEffect effect = new PooledEntityEffect(explosion);
//        effects.add(effect);
//    }
}
