package com.m12y.ld28.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Wall {
    public static final int TOP = 1;
    public static final int LEFT = 2;
    public static final int BOTTOM = 3;
    public static final int RIGHT = 4;
    public static final int TOP_LEFT = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM_RIGHT = 7;
    public static final int TOP_RIGHT = 8;
    public static final int TOP_LEFT_NUB = 9;
    public static final int BOTTOM_LEFT_NUB = 10;
    public static final int BOTTOM_RIGHT_NUB = 11;
    public static final int TOP_RIGHT_NUB = 12;

    public static final float PADDING = 1/8f;

    public Wall(int x, int y, int cellId) {
        ChainShape chain = null;

        switch (cellId) {
            case TOP:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x, y + 1 - PADDING),
                        new Vector2(x + 1, y + 1 - PADDING),
                });
                break;
            case LEFT:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + PADDING, y),
                        new Vector2(x + PADDING, y + 1),
                });
                break;
            case BOTTOM:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x, y + PADDING),
                        new Vector2(x + 1, y + PADDING),
                });
                break;
            case RIGHT:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + 1 - PADDING, y),
                        new Vector2(x + 1 - PADDING, y + 1),
                });
                break;
            case TOP_LEFT:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + PADDING, y),
                        new Vector2(x + PADDING, y + 1 - PADDING),
                        new Vector2(x + 1, y + 1 - PADDING),
                });
                break;
            case BOTTOM_LEFT:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + PADDING, y + 1),
                        new Vector2(x + PADDING, y + PADDING),
                        new Vector2(x + 1, y + PADDING),
                });
                break;
            case BOTTOM_RIGHT:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + 1 - PADDING, y + 1),
                        new Vector2(x + 1 - PADDING, y + PADDING),
                        new Vector2(x, y + PADDING),
                });
                break;
            case TOP_RIGHT:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + 1 - PADDING, y),
                        new Vector2(x + 1 - PADDING, y + 1 - PADDING),
                        new Vector2(x, y + 1 - PADDING),
                });
                break;
            case TOP_LEFT_NUB:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x, y + 1 - PADDING),
                        new Vector2(x + PADDING, y + 1 - PADDING),
                        new Vector2(x + PADDING, y + 1),
                });
                break;
            case BOTTOM_LEFT_NUB:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x, y + PADDING),
                        new Vector2(x + PADDING, y + PADDING),
                        new Vector2(x + PADDING, y),
                });
                break;
            case BOTTOM_RIGHT_NUB:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + 1 - PADDING, y),
                        new Vector2(x + 1 - PADDING, y + PADDING),
                        new Vector2(x + 1, y + PADDING),
                });
                break;
            case TOP_RIGHT_NUB:
                chain = new ChainShape();
                chain.createChain(new Vector2[]{
                        new Vector2(x + 1 - PADDING, y + 1),
                        new Vector2(x + 1 - PADDING, y + 1 - PADDING),
                        new Vector2(x + 1, y + 1 - PADDING),
                });
                break;
            default:
                System.out.println("Don't know how to draw chain for tile " + cellId);

        }

        if (chain != null) {
            BodyDef wallDef = new BodyDef();
            wallDef.type = BodyDef.BodyType.StaticBody;
            Body wallBody = LD28.instance.world.createBody(wallDef);

            wallBody.createFixture(chain, 0);

            chain.dispose();
        }
    }
}
