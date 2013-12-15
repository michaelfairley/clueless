package com.m12y.ld28.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.m12y.ld28.core.Clueless;

public class LD28Desktop {
    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL20 = true;
        config.width = Clueless.WIDTH;
        config.height = Clueless.HEIGHT;
        new LwjglApplication(new Clueless(), config);
    }
}
