package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.Note;
import com.aliyil.gmtkjam18.Utilities;
import com.aliyil.gmtkjam18.entity.interfaces.NoteBase;
import com.badlogic.gdx.graphics.Texture;

import java.util.LinkedList;

public class NoteSpawner extends Entity {
    private float timer;
    private float beatTimer;
    private int beatCounter;
    private float speed;
    public float noteLife;
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
        noteLife = 1f;
    }

    @Override
    public void tick() {
        super.tick();

        boolean spawnNote1 = false;
        boolean spawnNote2 = false;
        boolean spawnNote3 = false;
        boolean spawnNote4 = false;
        boolean spawnNoteBig = false;

        timer += dts();
        if(timer < 15) speed = 1;
        else speed = 1.5f;
        beatTimer += dts() * speed;
        noteLife = 2 - (timer / 200f);
        if(noteLife < 0.5f) noteLife = 0.5f;
//        noteLife = 0.5f;

        if(beatTimer > 1){
            beatTimer--;
            if(timer > 25 && Utilities.RANDOM.nextFloat() < 0.5f) beatTimer += 0.5f;
            beatCounter++;

            if(Utilities.RANDOM.nextFloat() <= 0.05f){
                spawnNoteBig = true;
            }else{
                switch (Utilities.RANDOM.nextInt(4)){
                    case 0:
                        spawnNote1 = true;
                        break;
                    case 1:
                        spawnNote2 = true;
                        break;
                    case 2:
                        spawnNote3 = true;
                        break;
                    case 3:
                        spawnNote4 = true;
                        break;
                }
            }



            if(timer > 45){
                switch (Utilities.RANDOM.nextInt(15)){
                    case 0:
                        spawnNote1 = true;
                        break;
                    case 1:
                        spawnNote2 = true;
                        break;
                    case 2:
                        spawnNote3 = true;
                        break;
                    case 3:
                        spawnNote4 = true;
                        break;
                }

                if(timer > 90){
                    switch (Utilities.RANDOM.nextInt(20)){
                        case 0:
                            spawnNote1 = true;
                            break;
                        case 1:
                            spawnNote2 = true;
                            break;
                        case 2:
                            spawnNote3 = true;
                            break;
                        case 3:
                            spawnNote4 = true;
                            break;
                    }

                    if(timer > 60){
                        switch (Utilities.RANDOM.nextInt(20)){
                            case 0:
                                spawnNote1 = true;
                                break;
                            case 1:
                                spawnNote2 = true;
                                break;
                            case 2:
                                spawnNote3 = true;
                                break;
                            case 3:
                                spawnNote4 = true;
                                break;
                        }
                    }
                }
            }
        }

        if(spawnNote1) spawn(Note.NOTE1);
        if(spawnNote2) spawn(Note.NOTE2);
        if(spawnNote3) spawn(Note.NOTE3);
        if(spawnNote4) spawn(Note.NOTE4);
        if(spawnNoteBig) spawn(Note.NOTEBIG);
    }

    @Override
    public void stop() {
        super.stop();
    }

    public NoteHit spawn(Note note){
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
                noteEntity = new NoteHit(getGameInstance(), getGameInstance().getResourceManager().circle, note, noteLife, health);
                break;
        }
        noteEntity.start();
        return noteEntity;
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
        return new NoteHit(getGameInstance(), noteTexture, note, noteLife, health);
    }
}
