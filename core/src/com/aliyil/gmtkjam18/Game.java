package com.aliyil.gmtkjam18;

import com.aliyil.gmtkjam18.entity.Entity;
import com.aliyil.gmtkjam18.entity.InputCalculator;
import com.aliyil.gmtkjam18.entity.Screen;
import com.aliyil.gmtkjam18.screen.LoadingScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Game extends ApplicationAdapter {
    public static final String ver = "0.0.1a";
    //Width units
    public static final int w = 640;
    //Height units
    public static final int h = 480;
    public static final boolean devMode = true;
    public SharedValues sharedValues;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private ParticleEffectManager particleEffectManager;
    private ResourceManager resourceManager;
    private SoundManager soundManager;
    private IOsBridge osBridge;

    private Screen currentScreen;
    private InputMultiplexer inputMultiplexer;
    private List<Entity> entities;
    private List<Entity> addQueue;
    private Iterator<Entity> currentIterator;

    public Game(IOsBridge osBridge) {
        this.osBridge = osBridge;
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<Entity>();
        addQueue = new ArrayList<Entity>();
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        sharedValues = new SharedValues();
        particleEffectManager = new ParticleEffectManager(this);
        resourceManager = new ResourceManager(this);
        soundManager = new SoundManager(this);

        resourceManager.loadResources();
        camera = new OrthographicCamera();
        camera.position.set(w / 2, h / 2, 0);
        viewport = new FitViewport(w, h, camera);
        viewport.apply();

        getSharedValues().bgColor =
                new Color(0.1f, 0.1f, 0.1f, 1f);
//                new Color(0.7f, 0.7f, 0.7f, 1f);

        Entity inputCalc = new InputCalculator(this, viewport);
        inputCalc.start();
        inputCalc.canRender = false;
        new LoadingScreen(this).start();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0,0 , 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
                | GL20.GL_DEPTH_BUFFER_BIT
                | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        //draw bg
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(getSharedValues().bgColor);
        shapeRenderer.rect(0, 0, w, h);
        shapeRenderer.end();

        spriteBatch.begin();
        getParticleEffectManager().renderAllParticles(spriteBatch);
        currentIterator = entities.listIterator();
        while (currentIterator.hasNext()) {
            Entity entity = currentIterator.next();
            if (!entity.isLiving()) {
                removeFromIterator();
            } else if (entity.isRunning()) {
                entity.tick();
            }
            if (entity.canRender)
                entity.render(spriteBatch);
        }
        currentIterator = null;
        spriteBatch.end();

        spriteBatch.setProjectionMatrix(camera.view);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        currentIterator = entities.listIterator();
        while (currentIterator.hasNext()) {
            Entity entity = currentIterator.next();
            if (entity.canRender) {
                shapeRenderer.setColor(entity.getColor());
                entity.shapeRender(shapeRenderer);
            }
        }
        currentIterator = null;
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        shapeRenderer.end();

        completeAdding();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        getResourceManager().dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        super.dispose();
    }

    public <TEntity extends Entity> void addEntity(TEntity entity) {
        addQueue.add(entity);
    }

    public void addToInputMultiplexer(Entity entity) {
        Array<InputProcessor> processors = inputMultiplexer.getProcessors();
        int indexToPut;
        for (indexToPut = 0; indexToPut < processors.size; indexToPut++) {
            int index = ((Entity) processors.get(indexToPut)).getInputIndex();
            if (index >= entity.getInputIndex()) {
                break;
            }
        }
        if (indexToPut >= processors.size) {
            inputMultiplexer.addProcessor(entity);
        } else {
            inputMultiplexer.addProcessor(indexToPut, entity);
        }

    }

    public void removeFromInputMultiplexer(Entity entity) {
        inputMultiplexer.removeProcessor(entity);
    }

    private void completeAdding() {
        for (Entity entity : addQueue) {
            ListIterator<Entity> iterator = entities.listIterator();
            while (iterator.hasNext()) {
                if (iterator.next().zIndex >= entity.zIndex)
                    break;
            }
            iterator.add(entity);
        }
        addQueue.clear();
    }

    public void removeFromIterator() {
        try {
            currentIterator.remove();
        } catch (IllegalStateException e) {
            Gdx.app.error("Warn", "currentIterator.remove() probably called multiple times", e);
        }

    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen screen) {
        currentScreen = screen;
    }

    public int getTotalRenderableCount() {
        return entities.size();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public ParticleEffectManager getParticleEffectManager() {
        return particleEffectManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public SharedValues getSharedValues() {
        return sharedValues;
    }

    public IOsBridge getOsBridge() {
        return osBridge;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
