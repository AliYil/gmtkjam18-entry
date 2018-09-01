package com.aliyil.gmtkjam18;

import com.badlogic.gdx.Gdx;

public final class S {
    //Delta Time
    public static float d() {
//        return Gdx.graphics.getDeltaTime();
        return 1f / 60f;
    }

    //Frames Per Second
    public static float fps() {
        return Gdx.graphics.getFramesPerSecond();
    }

    //Delta Time * Speed
    public static float dts(float speed) {
        return d() * speed;
    }
}
