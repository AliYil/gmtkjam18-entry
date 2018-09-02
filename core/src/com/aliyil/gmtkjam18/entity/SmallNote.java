package com.aliyil.gmtkjam18.entity;

import com.aliyil.gmtkjam18.Game;
import com.aliyil.gmtkjam18.Note;
import com.aliyil.gmtkjam18.Utilities;
import com.aliyil.gmtkjam18.entity.interfaces.NoteBase;
import com.badlogic.gdx.graphics.Texture;

public class SmallNote extends SpriteEntity implements NoteBase {
    private float life;
    private float startLife;
    private Note note;
    private float random;

    public SmallNote(Game game, Texture texture, Note note, float life) {
        super(game, texture);
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

        switch (note){
            case NOTE1:
                break;
            case NOTE2:
                break;
            case NOTE3:
                break;
            case NOTE4:
                setX(centerX + normalizedLife * centeredRandom * 40f);
                setY(centerY + normalizedLife*(centerY+10f));
                break;
        }

        getSprite().setRotation(getSprite().getRotation() + centeredRandom*10f);

        if(life <= -0.3f) hit();
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
        float normalizedAbsoulteLife = getNormalizedLife()*4f;
        normalizedAbsoulteLife = normalizedAbsoulteLife >= 0 ? normalizedAbsoulteLife : -normalizedAbsoulteLife;
        float alpha = 1 - normalizedAbsoulteLife;
        if(alpha > 1) alpha = 1;
        if(alpha < 0) alpha = 0;
        getGameInstance().getParticleEffectManager().newGrowingCircle(getX(), getY(), alpha);
        getGameInstance().getParticleEffectManager().newCircleExplosion(getX(), getY());
        return life;
    }

    @Override
    public Note getNote() {
        return note;
    }
}
