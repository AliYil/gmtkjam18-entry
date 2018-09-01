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

        if(additionalScale > 0) additionalScale *= 0.9f;
        else additionalScale = 0;

        getSprite().setScale(baseScale + additionalScale + (float)Math.sin(animationTime*3)/12f);
        super.tick();
    }

    public void growEffect(float amount){
        additionalScale += amount;
    }
}
