package com.aliyil.gmtkjam18.screen;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.entity.Circle;
import com.aliyil.gmtkjam18.entity.Screen;
import com.badlogic.gdx.Input;

public class InGame extends Screen {
    public Circle circle;

    public InGame(Game game) {
        super(game);
        enableInputListener(0);
    }

    @Override
    public void start() {
        getGameInstance().getSoundManager().noteBig();
        getGameInstance().getSoundManager().startBackground();
        circle = new Circle(getGameInstance());
        circle.setPosition(Game.w/2f, Game.h/2f);
        circle.start();
        super.start();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.DPAD_LEFT:
            case Input.Keys.A:
            case Input.Keys.NUMPAD_4:
                pressed(Note.NOTE1);
                break;
            case Input.Keys.DPAD_DOWN:
            case Input.Keys.S:
            case Input.Keys.NUMPAD_2:
                pressed(Note.NOTE2);
                break;
            case Input.Keys.DPAD_RIGHT:
            case Input.Keys.D:
            case Input.Keys.NUMPAD_6:
                pressed(Note.NOTE3);
                break;
            case Input.Keys.DPAD_UP:
            case Input.Keys.W:
            case Input.Keys.NUMPAD_8:
                pressed(Note.NOTE4);
                break;
            case Input.Keys.SPACE:
            case Input.Keys.ENTER:
            case Input.Keys.NUMPAD_5:
                pressed(Note.NOTEBIG);
                break;
        }
        return super.keyDown(keycode);
    }

    public void pressed(Note note){
        switch (note){
            case NOTE1:
                getGameInstance().getSoundManager().note1();
                circle.growEffect(0.2f);
                break;
            case NOTE2:
                getGameInstance().getSoundManager().note2();
                circle.growEffect(0.2f);
                break;
            case NOTE3:
                getGameInstance().getSoundManager().note3();
                circle.growEffect(0.2f);
                break;
            case NOTE4:
                getGameInstance().getSoundManager().note4();
                circle.growEffect(0.2f);
                break;
            case NOTEBIG:
                getGameInstance().getSoundManager().noteBig();
                circle.growEffect(0.6f);
                break;
        }
    }

    @Override
    public void stop() {
        super.stop();
        getGameInstance().getSoundManager().stopBackground();
        circle.kill();
    }

    enum Note{
        NOTE1,
        NOTE2,
        NOTE3,
        NOTE4,
        NOTEBIG
    }
}
