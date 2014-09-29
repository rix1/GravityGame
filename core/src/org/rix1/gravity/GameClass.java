package org.rix1.gravity;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.rix1.gravity.Entites.*;
import org.rix1.gravity.Utils.Axis;
import org.rix1.gravity.Utils.Direction;
import org.rix1.gravity.Utils.PlayerInfo;
import org.rix1.gravity.Utils.Utils;

public class GameClass extends ApplicationAdapter {
    SpriteBatch batch;
    int screenWidth;
    int screenHeight;
    int mapWidth = 15;
    int mapHeight = 15;
    int tileSize = 20;
    GameMap map;
    Player player;
    private HUD headsUpDisplay;

    private boolean restart = false;
    private boolean DEBUG_MODE = true;

    private int threshold = 4;

    boolean playMusic = false;

    float delta = 0;
    long tick = 0;
    Music music;

    ArrayList<StaticEntity> staticEntities = new ArrayList<StaticEntity>();
    ArrayList<MovableEntity> movableEntities = new ArrayList<MovableEntity>();


    public int getTileSize() {
        return tileSize;
    }

    public ArrayList<StaticEntity> getStaticEntities() {
        return staticEntities;
    }

    public ArrayList<MovableEntity> getMovableEntities() {
        return movableEntities;
    }

    @Override
    public void dispose(){
        Utils.saveGame(player);
    }

    @Override
    public void create () {
        batch = new SpriteBatch();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        map = new GameMap(mapHeight, mapWidth);

        // add some staticEntities including a player
        player = new Player(this, map.getStart()[0] * tileSize, map.getStart()[1] * tileSize, 20, 20, 120.0f);

        PlayerInfo p = Utils.loadGame();
        if(p != null)
            player.setPlayerInfo(p);

        movableEntities.add(player);

        // Static staticEntities
        staticEntities.add(new StaticEntity(this, 50, 150, tileSize, tileSize, new Texture(Gdx.files.internal("enemy.png"))));
        staticEntities.add(new StaticEntity(this, 200, 200, tileSize, tileSize, new Texture(Gdx.files.internal("enemy.png"))));
        staticEntities.add(new PowerUp(this, 180, 50, tileSize, tileSize, new Texture(Gdx.files.internal("block.png"))));

        // Goal
        staticEntities.add(new Goal(this, map.getEnd()[0]*tileSize, map.getEnd()[1]*tileSize, tileSize, tileSize, map.getSSTexture()));

        // HUD
        headsUpDisplay = new HUD(this);

        // This starts the music yo.
        if(!DEBUG_MODE) {
            startMusic();
        }
    }

    public void startMusic(){
        try {
            music = Gdx.audio.newMusic(Gdx.files.internal("whatsGolden.mp3"));
            playMusic = true;
        } catch (RuntimeException e) {
            System.out.println("Error when trying to read music file:\n\tFile not found. You have to download it form Rikard. lol that makes no sense, but he was too lazy to put it on Github.");;
            playMusic = false;
        }
        if(playMusic){
            music.setVolume(0.3f);
            music.play();
        }
    }


    public void restart(){
        player.incrementScore();
        restart = true;
        resetMap();
    }

    private void resetMap(){
        for (StaticEntity e:staticEntities){
            e.setVisible();
        }
    }

    public void moveEntity(Entity e, float newX, float newY) {
        // just check x collisions keep y the same
        moveEntityInAxis(e, Axis.X, newX, e.getY());
        // just check y collisions keep x the same
        moveEntityInAxis(e, Axis.Y, e.getX(), newY);
    }

    public void moveEntityInAxis(Entity e, Axis axis, float newX, float newY) {
        Direction direction;

        // determine axis direction
        if(axis == Axis.Y) {
            if(newY - e.getY() < 0) direction = Direction.U;
            else direction = Direction.D;
        }
        else {
            if(newX - e.getX() < 0) direction = Direction.L;
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
        int x1 = (int) Math.floor(Math.min(e.getX(), newX) / tileSize);
        int y1 = (int) Math.floor(Math.min(e.getY(), newY) / tileSize);
        int x2 = (int) Math.floor((Math.max(e.getX(), newX) + e.getWidth() - 0.1f) / tileSize);
        int y2 = (int) Math.floor((Math.max(e.getY(), newY) + e.getHeight() - 0.1f) / tileSize);


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
                if(newX < e2.getX() + e2.getWidth() && e2.getX() < newX + e1.getWidth() &&
                        newY < e2.getY() + e2.getHeight() && e2.getY() < newY + e1.getHeight()) {
                    collision = true;
                    e1.entityCollision(e2, newX, newY, direction);
                }
            }
        }

        return collision;
    }

    @Override
    public void render () {

        delta = Gdx.graphics.getDeltaTime();

        if(!restart){
            // Only update movable staticEntities
            for (Entity e : movableEntities){
                e = e instanceof Player ? ((Player) e) : ((MovableEntity) e);
                e.update(delta);
                moveEntity(e, e.getX() + e.getDx(), e.getY() + e.getDy());
            }
        }else{
            player.setPosition(map.getStart()[0]*tileSize, map.getStart()[1]*tileSize);
            restart = false;
        }


        Gdx.gl.glClearColor(0.20f, 0.20f, 0.20f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawMap();
        headsUpDisplay.drawLabels(Integer.toString(player.getScore()));

        // Draw all the staticEntities.
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
            if(e.isVisible())
                batch.draw(e.getTexture(), e.getX(), e.getY());
        }
    }

    public void drawPlayer(){
        Sprite playerSprite;
        if(tick%threshold == 0) {
            playerSprite = player.getNextSprite();
        }
        else playerSprite = player.getCurrentSprite();
        playerSprite.setBounds(player.getX(),player.getY(),60f,100f);
        playerSprite.draw(batch);
    }


        /* Variable music volume was a bad idea..
        if(player.isMoving() && playMusic){
            music.setVolume(0.8f);
        }else if(playMusic)
            music.setVolume(0.4f);
            }
        */

    public void drawMap(){
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if(map.getMap()[y][x]){
                    batch.draw(map.getTileTexture(), x * tileSize, y * tileSize);
                }
            }
        }
        if(DEBUG_MODE)
            batch.draw(map.getSSTexture(), map.getStart()[0]*tileSize, map.getStart()[1]*tileSize);
    }
}
