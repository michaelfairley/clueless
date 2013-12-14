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
    private Body body;

    @Override
    public void create () {
        texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2, 2);
        bodyDef.linearDamping = 4f;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.2f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();

        EdgeShape edge = new EdgeShape();
        edge.set(7, 1, 7, 6);

        BodyDef wallDef = new BodyDef();
        wallDef.type = BodyDef.BodyType.StaticBody;
        Body wallBody = world.createBody(wallDef);

        wallBody.createFixture(edge, 0);

        edge.dispose();
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
    }

    private void update() {
        world.step(1/60f, 6, 2);

        Vector2 vel = body.getLinearVelocity();
        Vector2 pos = body.getPosition();

        float maxVelocity = 10f;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && vel.x < maxVelocity) {
            body.applyLinearImpulse(1f, 0, pos.x, pos.y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && vel.x > -maxVelocity) {
            body.applyLinearImpulse(-1f, 0, pos.x, pos.y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && vel.y < maxVelocity) {
            body.applyLinearImpulse(0, 1f, pos.x, pos.y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && vel.y > -maxVelocity) {
            body.applyLinearImpulse(0, -1f, pos.x, pos.y, true);
        }
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
