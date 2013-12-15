package com.m12y.ld28.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player {
    Body body;

    private static float maxVelocity = 10f;
    private static float impulse = 1.5f;

    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(6, 6);
        bodyDef.linearDamping = 6f;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        body.createFixture(shape, 0);

        shape.dispose();
    }

    public Vector2 vel() {
        return body.getLinearVelocity();
    }

    public Vector2 pos() {
        return body.getPosition();
    }

    public void moveRight() {
        if(vel().x > maxVelocity) return;

        body.applyLinearImpulse(impulse, 0, pos().x, pos().y, true);
    }

    public void moveLeft() {
        if(vel().x < -maxVelocity) return;

        body.applyLinearImpulse(-impulse, 0, pos().x, pos().y, true);
    }

    public void moveUp() {
        if(vel().y > maxVelocity) return;

        body.applyLinearImpulse(0, impulse, pos().x, pos().y, true);
    }

    public void moveDown() {
        if(vel().y < -maxVelocity) return;

        body.applyLinearImpulse(0, -impulse, pos().x, pos().y, true);
    }
}
