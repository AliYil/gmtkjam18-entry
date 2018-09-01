package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.S;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DBG_TxInfo extends Text {
    float counter;
    float currentPeak;
    float peakDelta;
    Game game;
    DecimalFormat decimalFormat;
    Runtime runtime = Runtime.getRuntime();
    NumberFormat format = NumberFormat.getInstance();

    public DBG_TxInfo(Game game) {
        super(game, "");
        setScale(0.3f);
        setPosition(10, Game.h - 10);
        setAlpha(0.5f);
        decimalFormat = new DecimalFormat("#.##");
    }

    @Override
    public void start() {
        counter = 1;
        peakDelta = 0;
        currentPeak = 0;
        super.start();
    }

    @Override
    public void tick() {
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        counter -= S.d();
        if (counter < 0) {
            peakDelta = currentPeak;
            currentPeak = 0;
            counter = 1;
        }
        if (S.d() > currentPeak) currentPeak = S.d();
//        try {
        String text = "FPS: " + (int) S.fps();
        if (Game.devMode) {
            text += "  DLT: " + peakDelta
                    + "\nRENDERABLES: " + getGameInstance().getTotalRenderableCount()
                    + "\nPARTICLES: " + getGameInstance().getParticleEffectManager().DBG_getTotalParticles()
                    + "\nMEMORY: " + format.format((allocatedMemory - freeMemory) / 1024) + " / " + format.format(allocatedMemory / 1024);
        }
        setText(text);
//        } catch (Exception e) {
//            setText(e.toString());
//        }
    }
}
