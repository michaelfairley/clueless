package com.m12y.ld28.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.m12y.ld28.core.LD28;

public class LD28Desktop {
    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL20 = true;
        config.width = LD28.WIDTH;
        config.height = LD28.HEIGHT;
        new LwjglApplication(new LD28(), config);
    }
}
