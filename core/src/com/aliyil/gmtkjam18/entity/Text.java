package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;

public class Text extends Entity {
    public boolean isStatic;
    public float customWidth = 0;
    public float customHeight = 0;
    protected boolean dontOverlap = true;
    BitmapFont font;
    GlyphLayout glyphLayout;
    Rectangle boundingRectangle;
    private String text;
    private float scale;
    private boolean isCentered;
    private boolean isVerticalCentered;

    public Text(Game game, String text) {
        super(game);
        scale = 1f;
        boundingRectangle = new Rectangle();
        font = getGameInstance().getResourceManager().bitmapFont;
        font = new BitmapFont(font.getData(), font.getRegion(), false);
        setText(text);
        setStatic(false);
    }

    @Override
    public void start() {
        if (dontOverlap)
            fixOverlap();
        super.start();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateGlyphLayout();
        updateBoundingRectangle();
    }

    private void updateGlyphLayout() {
        font.getData().setScale(scale);
        if (glyphLayout == null) {
            glyphLayout = new GlyphLayout(font, this.text);
        } else {
            glyphLayout.setText(font, text);
        }
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        updateGlyphLayout();
        updateBoundingRectangle();
    }

    protected void updateBoundingRectangle() {
        boundingRectangle.setPosition(getDrawX(), getDrawY() - glyphLayout.height);
        boundingRectangle.setSize(glyphLayout.width - scale, glyphLayout.height);
        if (customWidth != 0) {
            boundingRectangle.setWidth(customWidth);
            boundingRectangle.setX(getX() - boundingRectangle.getWidth() / 2);
        }
        if (customHeight != 0) {
            boundingRectangle.setHeight(customHeight);
            boundingRectangle.setY(getY() - glyphLayout.height / 2 - boundingRectangle.getHeight() / 2);
        }
    }

    public void setCentered(boolean isCentered) {
        this.isCentered = isCentered;
        updateBoundingRectangle();
    }

    public void setVerticalCentered(boolean isVerticalCentered) {
        this.isVerticalCentered = isVerticalCentered;
        updateBoundingRectangle();
    }

    public Text setStatic(boolean isStatic) {
        this.isStatic = isStatic;
        return this;
    }

    private void fixOverlap() {
        if (boundingRectangle.overlaps(getSharedValues().leftRect)) {
            if (isCentered) {
                setX(boundingRectangle.getWidth() / 2);
            } else {
                setX(0);
            }

        } else if (boundingRectangle.overlaps(getSharedValues().rightRect)) {
            if (isCentered) {
                setX(Game.w - boundingRectangle.getWidth() / 2);
            } else {
                setX(Game.w - boundingRectangle.getWidth());
            }
        }

        if (boundingRectangle.overlaps(getSharedValues().upRect)) {
            if (isVerticalCentered) {
                setY(Game.h - boundingRectangle.getHeight() / 2);
            } else {
                setY(Game.h - boundingRectangle.getHeight());
            }
        }
    }

    @Override
    public void render(Batch batch) {
        if (canRender) {
            if (isStatic) {
                //TODO
            } else {
                font.setColor(getColor());
                font.getData().setScale(scale);
                font.draw(batch, text, getDrawX(), getDrawY());
            }
        }
    }

    public float getCenterX() {
        return getX() - glyphLayout.width / 2;
    }

    public float getCenterY() {
        return getY() + glyphLayout.height / 2;
    }

    public float getDrawX() {
        if (isCentered) {
            return getCenterX();
        } else
            return getX();
    }

    public float getDrawY() {
        if (isVerticalCentered) {
            return getCenterY();
        } else
            return getY();
    }

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        updateBoundingRectangle();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        updateBoundingRectangle();
    }

    public void setDontOverlap(boolean dontOverlap) {
        this.dontOverlap = dontOverlap;
    }
}
