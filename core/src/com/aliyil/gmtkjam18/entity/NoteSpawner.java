package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.Note;
import com.aliyil.gmtkjam18.Utilities;
import com.badlogic.gdx.graphics.Texture;

import java.util.LinkedList;

public class NoteSpawner extends Entity {
    private float timer;
    private float beatTimer;
    private int beatCounter;
    private float speed;
    private LinkedList<Note> notes;
    private Health health;

    public NoteSpawner(Game game, Health health) {
        super(game);
        this.health = health;
        speed = 1;
    }

    @Override
    public void start() {
        super.start();
        timer = 0;
        beatTimer = 0;
    }

    @Override
    public void tick() {
        super.tick();
        timer += dts();
        beatTimer += dts() * speed;

        if(beatTimer > 1){
            beatTimer--;
            beatCounter++;

            switch (beatCounter % 5){
                case 0:
                    spawn(Note.NOTE1);
                    break;
                case 1:
                    spawn(Note.NOTE2);
                    break;
                case 2:
                    spawn(Note.NOTE3);
                    break;
                case 3:
                    spawn(Note.NOTE4);
                    break;
                case 4:
                    spawn(Note.NOTEBIG);
                    break;
                case 5:
                    break;
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    private void spawn(Note note){
        NoteHit noteEntity = null;
        switch (note){
            case NONE:
                break;
            case NOTE1:
            case NOTE2:
            case NOTE3:
            case NOTE4:
                noteEntity = createSmallNote(note);
                break;
            case NOTEBIG:
                noteEntity = new NoteHit(getGameInstance(), getGameInstance().getResourceManager().circle, note, 1f, health);
                break;
        }
        noteEntity.start();
    }

    private NoteHit createSmallNote(Note note){
        Texture noteTexture;
        switch (Utilities.RANDOM.nextInt(3)){
            case 0:
                noteTexture = getGameInstance().getResourceManager().glowShape1;
                break;
            case 1:
                noteTexture = getGameInstance().getResourceManager().glowShape2;
                break;
            default:
                noteTexture = getGameInstance().getResourceManager().glowShape3;
                break;
        }
        return new NoteHit(getGameInstance(), noteTexture, note, 1f, health);
    }
}
