package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputCalculator extends Entity {
    Viewport viewport;

    public InputCalculator(Game game, Viewport viewport) {
        super(game);
        this.viewport = viewport;
        enableInputListener(-100);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        viewport.unproject(getSharedValues().touch.set(screenX, screenY));
        if (getSharedValues().touch.x < 0 || getSharedValues().touch.x > Game.w || getSharedValues().touch.y < 0 || getSharedValues().touch.y > Game.h)
            return true;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        viewport.unproject(getSharedValues().touch.set(screenX, screenY));
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        viewport.unproject(getSharedValues().touch.set(screenX, screenY));
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        viewport.unproject(getSharedValues().touch.set(screenX, screenY));
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
            return getGameInstance().getCurrentScreen().onBackPressed();
        }
        return super.keyDown(keycode);
    }
}
