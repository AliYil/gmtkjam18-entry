package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.Note;
import com.aliyil.gmtkjam18.Utilities;
import com.aliyil.gmtkjam18.entity.interfaces.NoteBase;
import com.badlogic.gdx.graphics.Texture;

public class NoteHit extends SpriteEntity implements NoteBase {
    private float life;
    private float startLife;
    private Note note;
    private float random;
    private Health health;

    public NoteHit(Game game, Texture texture, Note note, float life, Health health) {
        super(game, texture);
        this.health = health;
        resizeWidth(50);
        startLife = life;
        this.note = note;
    }

    @Override
    public void start() {
        super.start();
        life = startLife;
        random = Utilities.RANDOM.nextFloat();
    }

    @Override
    public void tick() {
        super.tick();
        life -= dts();
        float normalizedLife = getNormalizedLife();

        float centerX = Game.w/2f;
        float centerY = Game.h/2f;
        float centeredRandom = random-0.5f;
        float offset = 100f;
        float curveAmount = 40f;

        switch (note){
            case NOTE1:
                setY(centerY + normalizedLife * centeredRandom * curveAmount);
                setX(centerX - normalizedLife*(centerY+offset));
                break;
            case NOTE2:
                setX(centerX + normalizedLife * centeredRandom * curveAmount);
                setY(centerY - normalizedLife*(centerY+offset));
                break;
            case NOTE3:
                setY(centerY + normalizedLife * centeredRandom * curveAmount);
                setX(centerX + normalizedLife*(centerY+offset));
                break;
            case NOTE4:
                setX(centerX + normalizedLife * centeredRandom * curveAmount);
                setY(centerY + normalizedLife*(centerY+offset));
                break;
            case NOTEBIG:
                setX(centerX);
                setY(centerY);
                if(normalizedLife > 0){
                    getSprite().setScale(normalizedLife * 20f);
                    setAlpha(1 - normalizedLife);
                }
                break;
        }

        getSprite().setRotation(getSprite().getRotation() + centeredRandom*10f);

        if(life <= -0.2f) hit();
    }

    @Override
    public float getLife() {
        return life;
    }

    @Override
    public float getNormalizedLife() {
        return life / startLife;
    }

    @Override
    public float hit() {
        kill();
        getGameInstance().getParticleEffectManager().newCircleExplosion(getX(), getY());


        float normalizedLife = getNormalizedLife();
        float absoulteNormalizedLife = normalizedLife >= 0 ? normalizedLife : -normalizedLife;


        if(absoulteNormalizedLife < 0.1f){
            getSharedValues().score++;
            if(getNote() == Note.NOTEBIG){
                health.health += 0.05f;
            }
        }
        else{
            health.health -= 0.1f;
        }

        return absoulteNormalizedLife;
    }

    @Override
    public Note getNote() {
        return note;
    }
}
