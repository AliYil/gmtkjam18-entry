package com.aliyil.gmtkjam18;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public final class ResourceManager {
    //Resources
    public BitmapFont bitmapFont;

    public Texture circle;
    public Texture glowShape1;
    public Texture glowShape2;
    public Texture glowShape3;

    public Sound note1;
    public Sound note2;
    public Sound note3;
    public Sound note4;
    public Sound noteBig;
    public Sound backgroundSound;

    private Game gameInstance;
    private AssetManager assetManager;

    ResourceManager(Game gameInstance) {
        this.gameInstance = gameInstance;

        assetManager = new AssetManager();
        Texture.setAssetManager(assetManager);

    }

    void loadResources() {
        assetManager.load("fonts/font.fnt", BitmapFont.class);

        assetManager.load("textures/circle.png", Texture.class);
        assetManager.load("textures/glowshapes/glowshape1.png", Texture.class);
        assetManager.load("textures/glowshapes/glowshape2.png", Texture.class);
        assetManager.load("textures/glowshapes/glowshape3.png", Texture.class);

        assetManager.load("sounds/1.wav", Sound.class);
        assetManager.load("sounds/2.wav", Sound.class);
        assetManager.load("sounds/3.wav", Sound.class);
        assetManager.load("sounds/4.wav", Sound.class);
        assetManager.load("sounds/5.wav", Sound.class);
        assetManager.load("sounds/bg.wav", Sound.class);

        gameInstance.getParticleEffectManager().loadResources();
    }

    public float getProgress() {
        return assetManager.getProgress();
    }

    public void finishLoading() {
        bitmapFont = assetManager.get("fonts/font.fnt", BitmapFont.class);
        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        circle = assetManager.get("textures/circle.png", Texture.class);
        glowShape1 = assetManager.get("textures/glowshapes/glowshape1.png", Texture.class);
        glowShape2 = assetManager.get("textures/glowshapes/glowshape2.png", Texture.class);
        glowShape3 = assetManager.get("textures/glowshapes/glowshape3.png", Texture.class);
        circle.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        glowShape1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        glowShape2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        glowShape3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        note1 = assetManager.get("sounds/1.wav", Sound.class);
        note2 = assetManager.get("sounds/2.wav", Sound.class);
        note3 = assetManager.get("sounds/3.wav", Sound.class);
        note4 = assetManager.get("sounds/4.wav", Sound.class);
        noteBig = assetManager.get("sounds/5.wav", Sound.class);
        backgroundSound = assetManager.get("sounds/bg.wav", Sound.class);
    }

    void dispose() {
        assetManager.dispose();
        gameInstance.getParticleEffectManager().releaseResources();
    }

    public boolean isLoaded() {
        return assetManager.update();
    }
}
