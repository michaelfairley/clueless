package com.m12y.ld28.core;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class AI {
    Body body;
    long stoppedAt;
    static float velocity = 4;
    private World world;

    public AI(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 6);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        Fixture fixture = body.createFixture(shape, 1000000f);
        fixture.setRestitution(1f);

        shape.dispose();

        this.world = world;

        go();
    }

    public void go() {
        float newAngle = MathUtils.random(MathUtils.PI2);
        body.setTransform(body.getPosition(), newAngle);

        Vector2 newVelocity = (new Vector2(velocity, 0)).rotate(newAngle * MathUtils.radiansToDegrees);
        body.setLinearVelocity(newVelocity);
    }

    public void stop() {
        body.setLinearVelocity(new Vector2(0, 0));
        stoppedAt = System.nanoTime();
    }

    public void update() {
        if (isStopped()) {
            if (System.nanoTime() - stoppedAt > 500000000) go();
        } else {
            for (Contact contact: world.getContactList()) {
                if (!contact.isTouching()) continue;
                if (contact.getFixtureA().getBody() == body || contact.getFixtureB().getBody() == body) {
                    stop();
                }
            }
        }
    }

    public boolean isStopped() {
        return body.getLinearVelocity().len() < 0.01;
    }
}
