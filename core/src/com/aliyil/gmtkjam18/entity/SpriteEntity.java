package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SpriteEntity extends Entity {
    protected boolean canAutoUpdateCenter;
    protected float stateTime;
    protected boolean isAnimating;
    protected float aspectRatio = 1;
    private Sprite sprite;
    //Animation stuff
    private boolean isAnimated;
    private Animation<TextureAtlas.AtlasRegion> animation;
    private int latestKeyframeIndex;

    public SpriteEntity(Game game, Texture texture) {
        super(game);
        sprite = new Sprite(texture);
        sprite.setOriginCenter();
        canAutoUpdateCenter = true;
        aspectRatio = (float) texture.getWidth() / (float) texture.getHeight();
        setPosition(Game.w / 2, Game.h / 2);
    }

    public SpriteEntity(Game game, float frameDuration, Array<TextureAtlas.AtlasRegion> textures) {
        super(game);
        sprite = new Sprite(textures.get(0));
        sprite.setOriginCenter();
        canAutoUpdateCenter = true;
        aspectRatio = (float) textures.get(0).getRegionWidth() / (float) textures.get(0).getRegionHeight();
        setPosition(Game.w / 2, Game.h / 2);

        animation = new Animation<TextureAtlas.AtlasRegion>(frameDuration, textures);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        isAnimated = true;
        getSprite().setRegion(animation.getKeyFrame(0));
    }

    public Animation getAnimation() {
        if (isAnimated)
            return animation;
        else
            throw new IllegalStateException("Entity is not animated");
    }

    protected boolean isAnimated() {
        return isAnimated;
    }

    @Override
    public void start() {
        super.start();
        if (isAnimated)
            isAnimating = true;
    }

    @Override
    public void tick() {
        if (isAnimating) {
            stateTime += dts();
        }
        super.tick();
    }

    @Override
    public void render(Batch batch) {
        if (canRender) {
            if (isAnimating) {
                int oldIndex = latestKeyframeIndex;
                latestKeyframeIndex = animation.getKeyFrameIndex(stateTime);
                if (oldIndex != latestKeyframeIndex) {
                    getSprite().setRegion(animation.getKeyFrame(stateTime));
                }
            }
            sprite.setColor(getColor());
            sprite.setAlpha(getAlpha());
            sprite.draw(batch);
        }
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(
                x - sprite.getOriginX(),
                y - sprite.getOriginY());
    }

    @Override
    public Vector2 getPosVector() {
        return new Vector2(getX(), getY());
    }

    @Override
    public float getY() {
        return sprite.getY() + sprite.getOriginY();
    }

    @Override
    public void setY(float y) {
        sprite.setY(y - sprite.getOriginY());
    }

    @Override
    public float getX() {
        return sprite.getX() + sprite.getOriginX();
    }

    @Override
    public void setX(float x) {
        sprite.setX(x - sprite.getOriginX());
    }

    @Override
    public void translateX(float x) {
        sprite.translateX(x);
    }

    @Override
    public void translateY(float y) {
        sprite.translateY(y);
    }

    @Override
    public void translate(float x, float y) {
        sprite.translate(x, y);
    }

    //Resize without breaking aspect ratio
    public void resizeWidth(float width) {
        setSize(width, width / aspectRatio);
    }

    public void resizeHeight(float height) {
        setSize(height * aspectRatio, height);
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
        if (canAutoUpdateCenter)
            sprite.setOriginCenter();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean overlaps(SpriteEntity entity) {
        return getSprite().getBoundingRectangle().overlaps(entity.getSprite().getBoundingRectangle());
    }

    public boolean overlaps(Rectangle rect) {
        return getSprite().getBoundingRectangle().overlaps(rect);
    }
}