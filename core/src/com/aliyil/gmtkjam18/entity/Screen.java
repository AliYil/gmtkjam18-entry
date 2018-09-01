package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;

public abstract class Screen extends Entity {

    public Screen(Game game) {
        super(game);
    }

    @Override
    public void start() {
        super.start();
        if (getGameInstance().getCurrentScreen() != null && getGameInstance().getCurrentScreen().isLiving() && getGameInstance().getCurrentScreen() != this)
            getGameInstance().getCurrentScreen().kill();
        getGameInstance().setCurrentScreen(this);
    }

    public boolean onBackPressed() {
        return false;
    }
}
