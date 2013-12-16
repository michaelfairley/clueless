package com.m12y.ld28.core;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player {
    Body body;

    private static float maxVelocity = 10f;
    private static float impulse = 1.5f;
    static float halfSize = 0.5f;

    public Player(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(6, 16);
        bodyDef.linearDamping = 6f;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfSize, halfSize);

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

    public void arrest() {
        Vector2 position = body.getPosition();
        int deathCount = 0;

        AI closest = GameScreen.instance.ais.first();

        for(AI ai : GameScreen.instance.ais) {
            if (ai.dead) {
                deathCount++;
            } else if(ai.body.getPosition().dst(position) < closest.body.getPosition().dst(position)) {
                closest = ai;
            }
        }

        String people = deathCount == 1 ? "person" : "people";

        if (closest.killer) {
            Screen deaths = new TextScreen("Only " + deathCount + " " + people + " died!", closest.color, null);
            Screen winner = new TextScreen("You caught the killer!", closest.color, deaths);

            Clueless.instance.setScreen(winner);
        } else {
            Screen more = new TextScreen("And more surely will with the killer on the loose.", closest.color, null);
            Screen deaths = new TextScreen(Integer.toString(deathCount) + " " + people + " died!", closest.color, more);
            Screen wrong = new TextScreen("You got the wrong guy!", closest.color, deaths);
            Clueless.instance.setScreen(wrong);
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        Vector2 center = body.getPosition();

        shapeRenderer.rect(
                center.x - halfSize,
                center.y - halfSize,
                halfSize * 2,
                halfSize * 2
        );

        shapeRenderer.end();
    }
}
