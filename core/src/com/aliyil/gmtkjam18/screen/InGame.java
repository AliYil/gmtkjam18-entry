package com.aliyil.gmtkjam18.screen;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.Note;
import com.aliyil.gmtkjam18.entity.Circle;
import com.aliyil.gmtkjam18.entity.Health;
import com.aliyil.gmtkjam18.entity.NoteSpawner;
import com.aliyil.gmtkjam18.entity.Screen;
import com.aliyil.gmtkjam18.entity.Text;
import com.aliyil.gmtkjam18.entity.interfaces.NoteBase;
import com.aliyil.gmtkjam18.entity.Entity;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class InGame extends Screen {
    public Circle circle;
    private NoteSpawner spawner;
    private Health health;
    private Text scoreText;

    public static final float hitTolerance = 0.07f;

    public InGame(Game game) {
        super(game);
        enableInputListener(0);
    }

    @Override
    public void start() {
        getGameInstance().getSharedValues().score = 0;
        getGameInstance().getSoundManager().noteBig(1);
        getGameInstance().getSoundManager().startBackground();
        circle = new Circle(getGameInstance());
        circle.setPosition(Game.w/2f, Game.h/2f);
        circle.start();

        health = new Health(getGameInstance());
        health.setPosition(Game.w/2f, Game.h);
        health.health = 1f;
        health.start();

        spawner = new NoteSpawner(getGameInstance(), health);
        spawner.start();

        scoreText = new Text(getGameInstance(), "");
        scoreText.setCentered(true);
        scoreText.setVerticalCentered(true);
        scoreText.setPosition(Game.w/2f, Game.h/2f);
//        scoreText.setAlpha(0.3f);
        scoreText.getColor().a = 0.3f;
        scoreText.setScale(0.3f);
        scoreText.start();

        super.start();
    }

    @Override
    public void tick() {
        super.tick();

        if(health.health <= 0){
            new Main(getGameInstance()).start();
        }

        scoreText.setText(String.valueOf(getSharedValues().score));
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
        NoteBase closest = getClosest(note);
        float pitch = 1f;
        if(closest != null){
            float amount = closest.hit();

            if(amount < hitTolerance){
                float normalizedAbsoulteLife = amount*4f;
                float baseAlpha = closest.getNote() != Note.NOTEBIG ? 0.3f : 1f;
                float alpha = baseAlpha - normalizedAbsoulteLife;
                if(alpha > 1) alpha = 1;
                if(alpha < 0) alpha = 0;
                getGameInstance().getParticleEffectManager().newGrowingCircle(closest.getX(), closest.getY(), alpha);
            }else{
                pitch += closest.getNormalizedLife()/2f;
            }

        }
        switch (note){
            case NOTE1:
                getGameInstance().getSoundManager().note1(pitch);
                circle.growEffect(0.2f);
                break;
            case NOTE2:
                getGameInstance().getSoundManager().note2(pitch);
                circle.growEffect(0.2f);
                break;
            case NOTE3:
                getGameInstance().getSoundManager().note3(pitch);
                circle.growEffect(0.2f);
                break;
            case NOTE4:
                getGameInstance().getSoundManager().note4(pitch);
                circle.growEffect(0.2f);
                break;
            case NOTEBIG:
                getGameInstance().getSoundManager().noteBig(pitch);
                circle.growEffect(0.6f);
                break;
        }
    }

    private NoteBase getClosest(Note note){
        ArrayList<NoteBase> notes = new ArrayList<NoteBase>();
        for(Entity entity : getGameInstance().getEntities()){
            if(entity instanceof NoteBase){
                NoteBase noteEntity = (NoteBase)entity;
                if(noteEntity.getNote() == note){
                    notes.add(noteEntity);
                }
            }
        }

        NoteBase currentClosest = null;

        for(NoteBase noteEntity : notes){
            float normalizedLife = noteEntity.getNormalizedLife();
            float absoluteLife = normalizedLife >= 0 ? normalizedLife : -normalizedLife;
            float currentClosestAbsoluteLife = Float.MAX_VALUE;
            if(currentClosest != null){
                float currentClosestNormalizedLife = currentClosest.getNormalizedLife();
                currentClosestAbsoluteLife = currentClosestNormalizedLife >= 0 ? currentClosestNormalizedLife : -currentClosestNormalizedLife;
            }

            if(currentClosestAbsoluteLife > absoluteLife) currentClosest = noteEntity;
        }

        return currentClosest;
    }

    @Override
    public void stop() {
        super.stop();
        getGameInstance().getSoundManager().stopBackground();
        circle.kill();
        spawner.kill();
        health.kill();
        scoreText.kill();
    }
}
