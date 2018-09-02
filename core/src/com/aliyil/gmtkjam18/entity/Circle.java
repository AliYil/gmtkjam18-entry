package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;

public class Circle extends SpriteEntity {
    private float animationTime = 0;
    private static final double animationTimeLimit = Math.PI;
    private float baseScale = 0.8f;
    public float additionalScale = 0;

    public Circle(Game game) {
        super(game, game.getResourceManager().circle);

        resizeWidth(100);
    }

    @Override
    public void tick() {
        animationTime += dts();
        if(animationTime > animationTimeLimit) animationTime-=animationTimeLimit*2;

        if(additionalScale > 0.01f) additionalScale *= 0.9f;
        else additionalScale = 0;

        float calculated = additionalScale + (float)Math.sin(animationTime*3)/12f;

        getSprite().setScale(baseScale + calculated);

        float alpha = calculated*2 > 0.7f ? 0.7f : calculated*2;
        setAlpha(0.3f + alpha);
        super.tick();
    }

    public void growEffect(float amount){
        additionalScale += amount;
    }
}
