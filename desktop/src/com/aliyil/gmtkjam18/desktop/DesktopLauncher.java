package com.aliyil.gmtkjam18.desktop;

import com.aliyil.gmtkjam18.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.audioDeviceSimultaneousSources = 32;
        config.audioDeviceBufferSize = 1024;
        config.audioDeviceBufferCount = 18;
        new LwjglApplication(new Game(null), config);
    }
}
