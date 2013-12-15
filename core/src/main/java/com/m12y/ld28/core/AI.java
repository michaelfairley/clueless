package com.m12y.ld28.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Predicate;

public class AI {
    Body body;
    long stoppedAt;
    static float velocity = 3.5f;
    private Vector2 waypoint;

    public AI(Vector2 startingWaypoint) {
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

        shape.dispose();

        waypoint = startingWaypoint;
    }
    public void update() {
        if (isAtWaypoint()) pickNextWaypoint();

        Vector2 nextMove = new Vector2(waypoint).sub(body.getPosition()).clamp(velocity, velocity);
        body.setLinearVelocity(nextMove);
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
