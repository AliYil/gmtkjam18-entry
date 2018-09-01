package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.S;
import com.aliyil.gmtkjam18.SharedValues;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends InputAdapter {
    public float speedX;
    public float speedY;
    public float accX;
    public float accY;
    public int zIndex;
    public boolean canRender;
    protected boolean isLiving;
    private boolean isMoving;
    private boolean isRunning;
    private Vector2 pos;
    private float alpha = 1;
    private boolean isInputListener;
    private int inputIndex;
    private Game gameInstance;
    private Color color;

    public Entity(Game game) {
        this.gameInstance = game;
        game.addEntity(this);
        pos = new Vector2();
        isLiving = true;
        isInputListener = false;
        setColor(new Color(Color.WHITE));
    }

    public void start() {
        this.isRunning = true;
        this.canRender = true;
        if (isInputListener) {
            getGameInstance().addToInputMultiplexer(this);
        }
    }

    public void tick() {
        if (isMoving) {
            calculateMovings();
        }
    }

    public void stop() {
        canRender = false;
        isRunning = false;
        if (isInputListener) {
            getGameInstance().removeFromInputMultiplexer(this);
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    protected void enableInputListener(int index) {
        if (!isInputListener) {
            inputIndex = index;
            isInputListener = true;
            if (isRunning()) {
                getGameInstance().addToInputMultiplexer(this);
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    protected void calculateMovings() {
        speedX += accX * dts();
        speedY += accY * dts();
        translate(
                speedX * dts(),
                speedY * dts());
    }

    public void kill() {
        if (isLiving()) {
            isLiving = false;
            stop();
        }
    }

    public Vector2 getPosVector() {
        return pos;
    }

    public float getY() {
        return pos.y;
    }

    public void setY(float y) {
        this.pos.y = y;
        onPositionUpdate();
    }

    public float getX() {
        return pos.x;
    }

    public void setX(float x) {
        this.pos.x = x;
        onPositionUpdate();
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setPosition(Vector2 vec) {
        setPosition(vec.x, vec.y);
    }

    public void translateX(float x) {
        setX(getX() + x);
    }

    public void translateY(float y) {
        setY(getY() + y);
    }

    public void translate(float x, float y) {
        translateX(x);
        translateY(y);
    }

    protected void onPositionUpdate() {
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isLiving() {
        return isLiving;
    }

    public void render(Batch batch) {
    }

    public void shapeRender(ShapeRenderer shapeRenderer) {
    }

    public int getInputIndex() {
        return inputIndex;
    }

    //Utility Methods
    protected Game getGameInstance() {
        return gameInstance;
    }

    protected float dts() {
        return S.dts(getSharedValues().gameSpeed);
    }

    protected SharedValues getSharedValues() {
        return getGameInstance().sharedValues;
    }
}
