package com.m12y.ld28.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextScreen implements Screen {
    private final SpriteBatch batch;
    String text;
    Color color;
    Screen next;
    ShapeRenderer shapeRenderer;
    static String CONTINUE_TEXT = "[Press any key]";
    static String RESET_TEXT = "[Press R to play again]";

    public TextScreen(String text, Color color, Screen next) {
        this.text = text;
        this.color = color;
        this.next = next;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.begin();
        Clueless.instance.font.setColor(Color.BLACK);

        BitmapFont.TextBounds bounds = Clueless.instance.font.getBounds(text);
        Clueless.instance.font.draw(batch, text, Clueless.WIDTH/2 - bounds.width / 2, 400 - bounds.height);

        if (next != null) {
            BitmapFont.TextBounds continueBounds = Clueless.instance.font.getBounds(CONTINUE_TEXT);
            Clueless.instance.font.draw(batch, CONTINUE_TEXT, Clueless.WIDTH/2 - continueBounds.width/2, 100 - continueBounds.height);
        } else {
            BitmapFont.TextBounds resetBounds = Clueless.instance.font.getBounds(RESET_TEXT);
            Clueless.instance.font.draw(batch, RESET_TEXT, Clueless.WIDTH/2 - resetBounds.width/2, 100 - resetBounds.height);
        }

        batch.end();

        if (color != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(color);

            shapeRenderer.rect(
                    Clueless.WIDTH/2 - 128/2,
                    Clueless.HEIGHT - 300,
                    128,
                    128
            );

            shapeRenderer.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (next != null) {
                    Clueless.instance.setScreen(next);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
