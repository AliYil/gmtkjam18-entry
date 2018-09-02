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

    public NoteSpawner(Game game) {
        super(game);
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
            spawn(Note.NOTE4);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    private void spawn(Note note){
        switch (note){
            case NONE:
                break;
            case NOTE1:
            case NOTE2:
            case NOTE3:
            case NOTE4:
                SmallNote noteEntity = createSmallNote(note);
                noteEntity.start();
                break;
            case NOTEBIG:
                break;
        }
    }

    private SmallNote createSmallNote(Note note){
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
        return new SmallNote(getGameInstance(), noteTexture, note, 1f);
    }
}
