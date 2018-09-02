package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Health extends Entity {
    public float health = 0f;
    private float maxWidth = Game.w;
    private float drawWidth = 0f;
    private float speed = 1f;
    private float height = 20f;

    public Health(Game game) {
        super(game);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void tick() {
        float actualWidth = maxWidth * health;
        drawWidth += (actualWidth - drawWidth) / 4f;

        if(health > 1f) health = 1;
        super.tick();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void shapeRender(ShapeRenderer shapeRenderer) {
//        Color color = new Color(getColor());
//        color.a = 0.5f;
//        shapeRenderer.setColor(color);
        getColor().a = 0.4f;
        shapeRenderer.rect(getX() - drawWidth / 2f, getY() - height / 2f, drawWidth, height);
//        shapeRenderer.setColor(getColor().r, getColor().g, getColor().b, 0.2f);
//        shapeRenderer.rect(getX() - maxWidth / 2f, getY() - height/2f, maxWidth, height);
        super.shapeRender(shapeRenderer);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
