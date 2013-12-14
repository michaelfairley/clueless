package com.m12y.ld28.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class LD28 implements ApplicationListener {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    Texture texture;
    SpriteBatch batch;
    float elapsed;
    OrthographicCamera camera;
    World world;
    private Box2DDebugRenderer debugRenderer;
    private Player player;

    @Override
    public void create () {
        texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(world);

        ChainShape chain = new ChainShape();
        Vector2[] wall = new Vector2[]{
                new Vector2(10, 0),
                new Vector2(10, 10),
                new Vector2(0, 10),
                new Vector2(0, 20),
                new Vector2(-10, 20),
                new Vector2(-10, 10),
                new Vector2(-10, 0),
                new Vector2(0, 0),
                new Vector2(0, -10),
                new Vector2(10, -10),
        };
        chain.createLoop(wall);

        BodyDef wallDef = new BodyDef();
        wallDef.type = BodyDef.BodyType.StaticBody;
        Body wallBody = world.createBody(wallDef);

        wallBody.createFixture(chain, 0);

        chain.dispose();
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void render () {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();
        debugRenderer.render(world, camera.combined);
        update();
        camera.translate(player.body.getPosition().x - camera.position.x, player.body.getPosition().y - camera.position.y);
    }

    private void update() {
        world.step(1/60f, 6, 2);
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.moveLeft();
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.moveUp();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.moveDown();
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }
}
