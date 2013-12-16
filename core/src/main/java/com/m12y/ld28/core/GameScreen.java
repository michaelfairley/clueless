package com.m12y.ld28.core;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
    static GameScreen instance;

    SpriteBatch batch;
    float elapsed;
    OrthographicCamera camera;
    World world;
    private Box2DDebugRenderer debugRenderer;
    Player player;
    Array<AI> ais;
    OrthogonalTiledMapRenderer renderer;
    Array<Vector2> waypoints;
    ShapeRenderer shapeRenderer;

    public GameScreen() {
        instance = this;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16, 12);
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(world);

        TiledMap map = new TmxMapLoader().load("tiles/tiled.tmx");

        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get(0);


        for(int x = 0; x < wallLayer.getWidth(); x++) {
            for(int y = 0; y < wallLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = wallLayer.getCell(x, y);
                if (cell == null) continue;

                new Wall(x, y, cell.getTile().getId());
            }
        }

        MapLayer waypointLayer = map.getLayers().get(1);

        waypoints = new Array<Vector2>(false, wallLayer.getObjects().getCount());
        for (MapObject obj : waypointLayer.getObjects()) {
            RectangleMapObject waypointRect = (RectangleMapObject) obj;
            waypoints.add(waypointRect.getRectangle().getCenter(new Vector2()).div(64.0f));
        }

        Array<Vector2> startingWaypoints = new Array<Vector2>(waypoints);
        startingWaypoints.shuffle();

        int aiCount = 18;
        ais = new Array<AI>(false, aiCount);

        for (int i = 0; i < aiCount; i++) {
            ais.add(new AI(startingWaypoints.pop(), i == 0));
        }

        renderer = new OrthogonalTiledMapRenderer(map, 1/64f);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();
        camera.translate(player.body.getPosition().x - camera.position.x, player.body.getPosition().y - camera.position.y);
        renderer.setView(camera);
        renderer.render();
//        debugRenderer.render(world, camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        player.render(shapeRenderer);
        for(AI ai : new Array.ArrayIterator<AI>(ais)) ai.render(shapeRenderer);
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

        for(AI ai : new Array.ArrayIterator<AI>(ais)) ai.update();
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    player.arrest();
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
}
