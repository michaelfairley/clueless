package com.m12y.ld28.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Clueless extends Game {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.R)) setScreen(new GameScreen());
        super.render();
    }
}
