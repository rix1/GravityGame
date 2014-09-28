package org.rix1.gravity;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    int screenWidth;
    int screenHeight;
    int mapWidth = 15;
    int mapHeight = 15;
    int tileSize = 20;
    Texture tileTexture;
    GameMap map;
    Player player;

    boolean playMusic = false;

    int start = 4;
    int stop = 0;
    final int threshold = 5;

    float delta = 0;
    int posCounter = 0;
    int negCounter = 0;
    long tick = 0;
    Music music;

    ArrayList<StaticEntity> staticEntities = new ArrayList<StaticEntity>();
    ArrayList<MovableEntity> movableEntities = new ArrayList<MovableEntity>();

    enum Axis { X, Y };
    enum Direction { U, D, L, R };

    @Override
    public void create () {
        batch = new SpriteBatch();
        tileTexture = new Texture("block.png");

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        map = new GameMap(mapHeight, mapWidth);

        // add some staticEntities including a player
        player = new Player(this, 100, 150, 20, 20, 120.0f, "spriteShit.txt");
        movableEntities.add(player);

        // Static staticEntities
        staticEntities.add(new StaticEntity(this, 50, 150, 20, 20, new Texture(Gdx.files.internal("enemy.png"))));
        staticEntities.add(new StaticEntity(this, 200, 200, 20, 20, new Texture(Gdx.files.internal("enemy.png"))));
        staticEntities.add(new StaticEntity(this, 180, 50, 20, 20, new Texture(Gdx.files.internal("enemy.png"))));

        if(playMusic)
            startMusic();
    }

    public void startMusic(){
        music = Gdx.audio.newMusic(Gdx.files.internal("whatsGolden.mp3"));
        music.setVolume(0.3f);
        music.play();
    }



    public void moveEntity(Entity e, float newX, float newY) {
        // just check x collisions keep y the same
        moveEntityInAxis(e, Axis.X, newX, e.y);
        // just check y collisions keep x the same
        moveEntityInAxis(e, Axis.Y, e.x, newY);
    }

    public void moveEntityInAxis(Entity e, Axis axis, float newX, float newY) {
        Direction direction;

        // determine axis direction
        if(axis == Axis.Y) {
            if(newY - e.y < 0) direction = Direction.U;
            else direction = Direction.D;
        }
        else {
            if(newX - e.x < 0) direction = Direction.L;
            else direction = Direction.R;
        }

        if(!tileCollision(e, direction, newX, newY) && !entityCollision(e, direction, newX, newY)) {
            // full move with no collision
            e.move(newX, newY);
        }
        // else collision with wither tile or entity occurred
    }

    public boolean tileCollision(Entity e, Direction direction, float newX, float newY) {
        boolean collision = false;

        // determine affected tiles
        int x1 = (int) Math.floor(Math.min(e.x, newX) / tileSize);
        int y1 = (int) Math.floor(Math.min(e.y, newY) / tileSize);
        int x2 = (int) Math.floor((Math.max(e.x, newX) + e.width - 0.1f) / tileSize);
        int y2 = (int) Math.floor((Math.max(e.y, newY) + e.height - 0.1f) / tileSize);


        // tile checks
        for(int y = y1; y <= y2; y++) {
            for(int x = x1; x <= x2; x++) {
                if(map.getMap()[y][x]) {
                    collision = true;
                    e.tileCollision(x, y, newX, newY, direction);
                }
            }
        }
        return collision;
    }

    public boolean entityCollision(Entity e1, Direction direction, float newX, float newY) {
        boolean collision = false;

        for(int i = 0; i < staticEntities.size(); i++) {
            Entity e2 = staticEntities.get(i);

            // we don't want to check for collisions between the same entity
            if(e1 != e2) {
                // axis aligned rectangle rectangle collision detection
                if(newX < e2.x + e2.width && e2.x < newX + e1.width &&
                        newY < e2.y + e2.height && e2.y < newY + e1.height) {
                    collision = true;

                    e1.entityCollision(e2, newX, newY, direction);
                }
            }
        }

        return collision;
    }

    @Override
    public void render () {

        // update
        // ---

        delta = Gdx.graphics.getDeltaTime();

        // Only update movable staticEntities
        for (Entity e : movableEntities){
            e.update(delta);
            moveEntity(e, e.x + e.dx, e.y + e.dy);
        }


        // to offset where your map and staticEntities are drawn change the viewport
        // see libgdx documentation

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawMap();

        // Draw all the staticEntities.
        // TODO: Need some new way for handling sprites that are animated
        drawStaticEntities();
        drawMovingEntities();

        tick++;
        batch.end();
    }

    public void drawMovingEntities(){
        drawPlayer();
    }

    public void drawStaticEntities(){
        for (StaticEntity e:staticEntities){
            batch.draw(e.getTexture(), e.x, e.y);
        }
    }

    public void drawPlayer(){
        Direction d = player.getCurrentDir();
        Array<Sprite> sprites = player.getSprites();

        if(tick%threshold == 0){
            posCounter++;
            negCounter--;

            if(posCounter >= sprites.size){
                posCounter = start;
            }

            if(negCounter <= start){
                negCounter = sprites.size-1;
            }
        }

        if(player.isMoving() && playMusic){
            music.setVolume(0.6f);
        }else if(playMusic)
        music.setVolume(0.4f);

        switch (d){
            case U:
                if(player.isMoving()){
                    sprites.get(1).setPosition(player.x, player.y);
                    sprites.get(1).draw(batch);
                }else{
                    sprites.get(1).setPosition(player.x, player.y);
                    sprites.get(1).draw(batch);
                }
                break;
            case D:
                if(player.isMoving()){
                    sprites.get(0).setPosition(player.x, player.y);
                    sprites.get(0).draw(batch);
                }else{
                    sprites.get(0).setPosition(player.x, player.y);
                    sprites.get(0).draw(batch);
                }
                break;
            case L:
                if(player.isMoving()){
                    sprites.get(negCounter).setPosition(player.x, player.y);
                    sprites.get(negCounter).draw(batch);
                }else{
                    negCounter = sprites.size-1;
                    sprites.get(0).setPosition(player.x, player.y);
                    sprites.get(0).draw(batch);
                }
                break;
            case R:
                if(player.isMoving()){
                    sprites.get(posCounter).setPosition(player.x, player.y);
                    sprites.get(posCounter).draw(batch);
                }else{
                    posCounter = 0;
                    // 1 sprite is the
                    sprites.get(0).setPosition(player.x, player.y);
                    sprites.get(0).draw(batch);
                }
                break;
            default:
                // set start stop for up
                break;
        }
    }

    public void drawMap(){
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if(map.getMap()[y][x]){
                    batch.draw(tileTexture, x * tileSize, y * tileSize);
                }
            }
        }
    }
}
