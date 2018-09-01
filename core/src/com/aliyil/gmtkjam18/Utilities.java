package com.aliyil.gmtkjam18;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public final class Utilities {
    public static final Random RANDOM = new Random();

    public static void drawRectLine(ShapeRenderer shapeRenderer, Rectangle rect, float width) {
        drawRectLine(shapeRenderer,
                rect.getX(),
                rect.getX() + rect.getWidth(),
                rect.getY(),
                rect.getY() + rect.getHeight(),
                width);
    }

    public static void drawRectLine(ShapeRenderer shapeRenderer, float x1, float y1, float x2, float y2, float width) {
        float half = width / 2f;
        shapeRenderer.rectLine(x1 + half, x2 + half, x1 + half, y2 - half, width);
        shapeRenderer.rectLine(y1 - half, y2 - half, y1 - half, x2 + half, width);
        shapeRenderer.rectLine(x1, y2 - half, y1, y2 - half, width);
        shapeRenderer.rectLine(y1, x2 + half, x1, x2 + half, width);
    }

    public static void setRandomColor(Color color) {
        if (RANDOM.nextInt(10) == 0) {
            color.set(Color.WHITE);
            return;
        }
        float a, b, c;
        a = (float) Math.random();
        b = (float) Math.random();
        c = 2f - (a + b);
        if (c > 1f) {
            float excess = c - 1f;
            c = 1f;
            a += excess / 2f;
            b += excess / 2f;
        }
        switch (RANDOM.nextInt(3)) {
            case 0:
                color.set(c, a, b, 1);
                break;
            case 1:
                color.set(a, c, b, 1);
                break;
            default:
                color.set(a, b, c, 1);
                break;
        }
    }

    public static float calculateColorDiff(Color col1, Color col2) {
        float redDiff = col1.r - col2.r;
        float greenDiff = col1.g - col2.g;
        float blueDiff = col1.b - col2.b;
        if (redDiff < 0) redDiff *= -1;
        if (greenDiff < 0) greenDiff *= -1;
        if (blueDiff < 0) blueDiff *= -1;
        return redDiff + greenDiff + blueDiff;
    }

    public static void setRainbowEffectToParticle(ParticleEffect effect) {
        setRainbowEffectToParticle(effect, 10);
    }

    public static void setRainbowEffectToParticle(ParticleEffect effect, int step) {
        float[] color = new float[step * 3];
        float[] colorTimeline = new float[step];
        Color col = new Color();
        for (int i = 0; i < step; i++) {
            colorTimeline[i] = (float) i / (float) step;
            setRandomColor(col);
            color[i * 3] = col.r;
            color[i * 3 + 1] = col.g;
            color[i * 3 + 2] = col.b;
        }
        for (ParticleEmitter emitter : effect.getEmitters()) {
            emitter.getTint().setColors(color);
            emitter.getTint().setTimeline(colorTimeline);
        }
    }

    public static Rectangle scaleRectangle(Rectangle rect, float scale) {
        Rectangle rect2 = new Rectangle(rect);
        float oldWidth = rect2.getWidth();
        float oldHeight = rect2.getHeight();
        float newWidth = rect2.getWidth() * scale;
        float newHeight = rect2.getHeight() * scale;
        rect2.setSize(newWidth, newHeight);
        rect2.setPosition(rect.getX() - (newWidth - oldWidth) / 2, rect.getY() - (newHeight - oldHeight) / 2);
        return rect2;
    }

    public static Rectangle enlargeRectangle(Rectangle rect, float amount) {
        Rectangle rect2 = new Rectangle(rect);
        rect2.setPosition(rect.getX() - amount, rect.getY() - amount);
        rect2.setSize(rect.getWidth() + amount * 2, rect.getHeight() + amount * 2);
        return rect2;
    }
}
