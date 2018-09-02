package com.aliyil.gmtkjam18.screen;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.Note;
import com.aliyil.gmtkjam18.entity.Circle;
import com.aliyil.gmtkjam18.entity.Entity;
import com.aliyil.gmtkjam18.entity.NoteHit;
import com.aliyil.gmtkjam18.entity.NoteSpawner;
import com.aliyil.gmtkjam18.entity.Screen;
import com.aliyil.gmtkjam18.entity.Text;
import com.aliyil.gmtkjam18.entity.interfaces.NoteBase;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class Tutorial extends Screen {
    public Circle circle;
    private float timer = 0;
    private int tutorialStage = 0;
    NoteSpawner spawner;
    NoteHit tutorialNote;
    Text tutorialText;

    public static final float hitTolerance = 0.07f;

    public Tutorial(Game game) {
        super(game);
        enableInputListener(0);
    }

    @Override
    public void start() {
        getGameInstance().getSharedValues().score = 0;
        getGameInstance().getSoundManager().noteBig(1);
        circle = new Circle(getGameInstance());
        circle.setPosition(Game.w/2f, Game.h/2f);
        circle.start();
        timer = 0;
        tutorialStage = 0;

        spawner = new NoteSpawner(getGameInstance(), null);
        spawner.noteLife = 2f;

        tutorialNote = null;

        tutorialText = new Text(getGameInstance(), "");
        tutorialText.setCentered(true);
        tutorialText.setVerticalCentered(true);
        tutorialText.setScale(0.4f);
        super.start();
    }

    @Override
    public void tick() {
        super.tick();
        timer += dts();

        if(tutorialStage <= 0){
            tutorialStage = 1;
            tutorialNote = spawner.spawn(Note.NOTE1);
        }
        else if(tutorialStage == 1 && tutorialNote.getLife() <= 0){
            getSharedValues().gameSpeed = 0; //pause the game

            tutorialText.setText("Press Left");
            tutorialText.start();
            tutorialText.setPosition(Game.w * 0.25f, Game.h * 0.5f);

            if(!tutorialNote.isLiving()){
                tutorialStage = 2;
                tutorialText.stop();
                getSharedValues().gameSpeed = 1; //resume the game

                tutorialNote = spawner.spawn(Note.NOTE2);
            }
        }
        else if(tutorialStage == 2 && tutorialNote.getLife() <= 0){
            getSharedValues().gameSpeed = 0; //pause the game

            tutorialText.setText("Press Down");
            tutorialText.start();
            tutorialText.setPosition(Game.w * 0.5f, Game.h * 0.25f);

            if(!tutorialNote.isLiving()){
                tutorialStage = 3;
                tutorialText.stop();
                getSharedValues().gameSpeed = 1; //resume the game

                tutorialNote = spawner.spawn(Note.NOTE3);
            }
        }
        else if(tutorialStage == 3 && tutorialNote.getLife() <= 0){
            getSharedValues().gameSpeed = 0; //pause the game

            tutorialText.setText("Press Right");
            tutorialText.start();
            tutorialText.setPosition(Game.w * 0.75f, Game.h * 0.5f);

            if(!tutorialNote.isLiving()){
                tutorialStage = 4;
                tutorialText.stop();
                getSharedValues().gameSpeed = 1; //resume the game

                tutorialNote = spawner.spawn(Note.NOTE4);
            }
        }
        else if(tutorialStage == 4 && tutorialNote.getLife() <= 0){
            getSharedValues().gameSpeed = 0; //pause the game

            tutorialText.setText("Press Up");
            tutorialText.start();
            tutorialText.setPosition(Game.w * 0.5f, Game.h * 0.75f);

            if(!tutorialNote.isLiving()){
                tutorialStage = 5;
                tutorialText.stop();
                getSharedValues().gameSpeed = 1; //resume the game

                tutorialNote = spawner.spawn(Note.NOTEBIG);
            }
        }
        else if(tutorialStage == 5 && tutorialNote.getLife() <= 0){
            getSharedValues().gameSpeed = 0; //pause the game

            tutorialText.setText("Press Space");
            tutorialText.start();
            tutorialText.setPosition(Game.w * 0.5f, Game.h * 0.75f);

            if(!tutorialNote.isLiving()){
                tutorialStage = 6;
                tutorialText.stop();
                getSharedValues().gameSpeed = 1; //resume the game

                new InGame(getGameInstance()).start();
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(getSharedValues().gameSpeed == 0){
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
            }

        }

        switch (note){
            case NOTE1:
                getGameInstance().getSoundManager().note1(1);
                circle.growEffect(0.2f);
                break;
            case NOTE2:
                getGameInstance().getSoundManager().note2(1);
                circle.growEffect(0.2f);
                break;
            case NOTE3:
                getGameInstance().getSoundManager().note3(1);
                circle.growEffect(0.2f);
                break;
            case NOTE4:
                getGameInstance().getSoundManager().note4(1);
                circle.growEffect(0.2f);
                break;
            case NOTEBIG:
                getGameInstance().getSoundManager().noteBig(1);
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
        circle.kill();
        spawner.kill();
    }
}
