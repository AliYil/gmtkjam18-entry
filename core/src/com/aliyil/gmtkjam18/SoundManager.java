package com.aliyil.gmtkjam18;

import com.badlogic.gdx.audio.Sound;

public final class SoundManager {
    private Game gameInstance;

    SoundManager(Game gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void note1() {
        float vol = 1f;
        Sound s = gameInstance.getResourceManager().note1;
        s.play(vol);
    }

    public void note2() {
        float vol = 1f;
        Sound s = gameInstance.getResourceManager().note2;
        s.play(vol);
    }

    public void note3() {
        float vol = 1f;
        Sound s = gameInstance.getResourceManager().note3;
        s.play(vol);
    }

    public void note4() {
        float vol = 1f;
        Sound s = gameInstance.getResourceManager().note4;
        s.play(vol);
    }

    public void noteBig() {
        float vol = 1f;
        Sound s = gameInstance.getResourceManager().noteBig;
        s.play(vol);
    }

    public void startBackground() {
        float vol = 1f;
        Sound s = gameInstance.getResourceManager().backgroundSound;
        s.loop(vol);
    }

    public void stopBackground() {
        Sound s = gameInstance.getResourceManager().backgroundSound;
        s.stop();
    }
}
