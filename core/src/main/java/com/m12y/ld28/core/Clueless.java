package com.m12y.ld28.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Clueless extends Game {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    BitmapFont font;

    static Clueless instance;

    @Override
    public void create() {
        FileHandle fontFile = Gdx.files.internal("verdana.fnt");
        FileHandle fontImageFile = Gdx.files.internal("verdana.png");
        font = new BitmapFont(fontFile, fontImageFile, false);

        instance = this;

        Screen go = new TextScreen("Party time!", null, new GameScreen());
        Screen reset = new TextScreen("Press R at any time to start over", null, go);
        Screen space = new TextScreen("Press space to make the arrest", null, reset);
        Screen arrows = new TextScreen("Move with WASD or the arrow keys", null, space);
//        Screen graphics = new TextScreen("(The graphics in this game are really not good)", Color.ORANGE, arrows);
        Screen arrest = new TextScreen("And then arrest them", Color.GREEN, arrows);
        Screen mission = new TextScreen("Your goal is to figure out who the killer is...", Color.WHITE, arrest);
        Screen strike = new TextScreen("The killer will strike", Color.YELLOW, mission);
        Screen alone = new TextScreen("If left alone with another guest...", Color.MAGENTA, strike);
        Screen killer = new TextScreen("Except that one of them is a killer", Color.CYAN, alone);
        Screen nice = new TextScreen("They're nice enough", Color.RED, killer);
        Screen goers = new TextScreen("These are some of the other partygoers", Color.BLUE, nice);
        Screen cop = new TextScreen("You're a cop, and you're at a party", Color.BLACK, goers);
        Screen you = new TextScreen("This is you", Color.BLACK, cop);
        Screen hello = new TextScreen("Hello", null, you);

        setScreen(hello);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Input.Keys.R)) setScreen(new GameScreen());
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
