package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;

public class DBG_Test extends Entity {
    public DBG_Test(Game game) {
        super(game);
    }

    @Override
    public void start() {
        super.start();
        setPosition(Game.w / 2, Game.h / 2);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
