package com.aliyil.gmtkjam18.screen;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.entity.Screen;
import com.aliyil.gmtkjam18.entity.Text;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

public class Main extends Screen {
    private Text text1;
    private Text text2;

    public Main(Game game) {
        super(game);
        enableInputListener(0);
    }

    @Override
    public void start() {
        super.start();
        text1 = new Text(getGameInstance(), "");
        text1.setPosition(Game.w * 0.5f, Game.h * 0.85f);
        text1.setCentered(true);
        text1.setColor(new Color(Color.WHITE));
        text1.start();

        text2 = new Text(getGameInstance(), "");
        text2.setPosition(Game.w * 0.5f, Game.h * 0.7f);
        text2.setScale(0.5f);
        text2.setCentered(true);
        text2.setColor(new Color(Color.WHITE));
        text2.start();

        if (getSharedValues().score <= 0) {
            text1.setScale(0.5f);
            text1.setText("PERMANOTES");
            text2.setText("Press Space");
        } else {
            text1.setScale(0.5f);
            text1.setText("TRY AGAIN");
            text2.setText("YOUR SCORE: " + getSharedValues().score);
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.SPACE:
                if (getSharedValues().score <= 0){
                    new Tutorial(getGameInstance()).start();
                }else{
                    new InGame(getGameInstance()).start();
                }
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void stop() {
        super.stop();
        text1.kill();
        text2.kill();
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }
}
