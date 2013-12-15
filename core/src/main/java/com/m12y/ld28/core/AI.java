package com.m12y.ld28.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;

public class AI {
    Body body;
    static float velocity = 3.5f;
    private Vector2 waypoint;
    boolean killer;
    static float seeRadius = 10f;
    static float killRadius = 3f;
    boolean dead = false;

    public AI(Vector2 startingWaypoint) {
        this(startingWaypoint, false);
    }

    public AI(Vector2 startingWaypoint, boolean killer) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(startingWaypoint.x, startingWaypoint.y);
        bodyDef.fixedRotation = true;
        bodyDef.linearDamping = 1f;
        body = LD28.instance.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setRestitution(1f);
        fixture.setSensor(true);

        shape.dispose();

        waypoint = startingWaypoint;
        this.killer = killer;
    }
    public void update() {
        if (dead) return;
        if (isAtWaypoint()) pickNextWaypoint();
        if (killer) tryToKill();

        Vector2 nextMove = new Vector2(waypoint).sub(body.getPosition()).clamp(velocity, velocity);
        body.setLinearVelocity(nextMove);
    }

    private void tryToKill() {
        int seeable = 0;
        Array<AI> killable = new Array<AI>();

        Vector2 position = body.getPosition();

        if (LD28.instance.player.body.getPosition().dst(position) < seeRadius) seeable++;

        for (AI ai : LD28.instance.ais) {
            if (ai == this) continue;
            if (ai.dead) continue;
            Vector2 aiPosition = ai.body.getPosition();
            if (position.dst(aiPosition) < seeRadius) seeable++;
            if (position.dst(aiPosition) < killRadius) killable.add(ai);
        }

        if (seeable == 1 && killable.size == 1) {
            killable.first().die();
        }
    }

    private void die() {
        System.out.println("Killed");
        dead = true;
        body.setLinearVelocity(new Vector2(0, 0));
        body.getFixtureList().first().setSensor(true);
    }

    private void pickNextWaypoint() {
        final Vector2 position = body.getPosition();
        LD28.instance.waypoints.shuffle();

        Iterable<Vector2> nearby = LD28.instance.waypoints.select(new Predicate<Vector2>() {
            @Override
            public boolean evaluate(Vector2 candidate) {
                float distance = candidate.dst(position);
                return distance < 4 && distance > 1;
            }
        });

        waypoint = nearby.iterator().next();
    }

    private boolean isAtWaypoint() {
        return waypoint.dst(body.getPosition()) < 0.1;
    }
}
