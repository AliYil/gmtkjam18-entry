package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.OnClickListener;
import com.aliyil.gmtkjam18.Utilities;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class ClickableSprite extends SpriteEntity {
    public int inpIndex;
    public float clickableAreaScale = 1;
    public Rectangle clickableArea;
    public boolean holding;
    public int pointer;
    private OnClickListener listener;

    public ClickableSprite(Game game, Texture texture) {
        super(game, texture);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void start() {
        enableInputListener(inpIndex);
        super.start();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!isRunning()) return false;
        Rectangle rect;
        if (clickableArea == null) {
            rect = Utilities.scaleRectangle(getSprite().getBoundingRectangle(), clickableAreaScale);
        } else {
            rect = clickableArea;
        }

        if (rect.contains(getSharedValues().touch.x, getSharedValues().touch.y)) {
            this.pointer = pointer;
            holding = true;
            if (listener == null) return false;
            return listener.onClick();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (this.pointer == pointer) holding = false;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    public void click() {
        if (listener != null) listener.onClick();
    }
}
