package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.Utilities;
import com.badlogic.gdx.graphics.Color;

public class Rainbow extends Entity {
    public float speed;
    public boolean respectPause = false;
    private Color originColor;
    private Color targetColor;
    private Color colorInstance;
    private float timer;

    public Rainbow(Game game, Color colorInstance) {
        super(game);
        this.colorInstance = colorInstance;
        targetColor = new Color();
        originColor = new Color(this.colorInstance);
        speed = 0.2f;
    }

    @Override
    public void start() {
        Utilities.setRandomColor(originColor);
        Utilities.setRandomColor(targetColor);
        timer = 0;
        super.start();
    }

    @Override
    public void tick() {
        if (respectPause && getSharedValues().paused) return;
        timer += dts() * speed;

        if (timer > 1f) {
            timer -= 1f;
            Color oldColor = new Color(targetColor);
            Utilities.setRandomColor(targetColor);
            originColor.set(oldColor);

        } else {
            colorInstance.set(originColor.cpy().lerp(targetColor, timer));
        }
        super.tick();
    }

    public Color getColorInstance() {
        return colorInstance;
    }
}
