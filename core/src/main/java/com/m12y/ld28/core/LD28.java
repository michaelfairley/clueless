package com.m12y.ld28.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class LD28 implements ApplicationListener {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    Texture texture;
    SpriteBatch batch;
    float elapsed;
    OrthographicCamera camera;
    static World world;
    private Box2DDebugRenderer debugRenderer;
    private Player player;
    private AI ai;
    OrthogonalTiledMapRenderer renderer;

    @Override
    public void create () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(world);

        TiledMap map = new TmxMapLoader().load("tiles/tiled.tmx");

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        for(int x = 0; x < layer.getWidth(); x++) {
            for(int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell == null) continue;

                new Wall(x, y, cell.getTile().getId());
            }
        }

        renderer = new OrthogonalTiledMapRenderer(map, 1/64f);
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void render () {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();
        camera.translate(player.body.getPosition().x - camera.position.x, player.body.getPosition().y - camera.position.y);
        renderer.setView(camera);
        renderer.render();
        debugRenderer.render(world, camera.combined);
        update();
    }

    private void update() {
        world.step(1/60f, 6, 2);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.D))     player.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  player.moveLeft();
        if (Gdx.input.isKeyPressed(Input.Keys.A))     player.moveLeft();
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    player.moveUp();
        if (Gdx.input.isKeyPressed(Input.Keys.W))     player.moveUp();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  player.moveDown();
        if (Gdx.input.isKeyPressed(Input.Keys.S))     player.moveDown();

//        ai.update();
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }
}