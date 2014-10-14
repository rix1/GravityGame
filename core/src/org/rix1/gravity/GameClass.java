package org.rix1.gravity;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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
    int tileSize = Utils.tileSize;
    int gameCount = 0;
    GameMap map;
    Player player;
    private HUD headsUpDisplay;
    boolean isSpacePressed = false;

    private boolean restart = false;
    private boolean DEBUG_MODE = false; // THIS TURNS ON EXTRA DEV STUFF AND CUTS THE MUSIC

    private int threshold = 4;

    boolean playMusic = false;

    float delta = 0;
    long tick = 0;
    Music music;

    ArrayList<StaticEntity> staticEntities = new ArrayList<StaticEntity>();
    ArrayList<MovableEntity> movableEntities = new ArrayList<MovableEntity>();

    public int[][] getLogicalMap() {
        return map.getMap();
    }

    public GameMap getMap() {
        return map;
    }

    public int getTileSize() {
        return tileSize;
    }

    public ArrayList<StaticEntity> getStaticEntities() {
        return staticEntities;
    }

    public ArrayList<MovableEntity> getMovableEntities() {
        return movableEntities;
    }

    public long getTick() {
        return tick;
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

        movableEntities.add(new EnemyEntity(this, 20, 110, tileSize, tileSize, 90f));

        // Add some walls:
        for (int i = 4; i < 12; i++) {
            getLogicalMap()[7][i] = 1;
        }
        getLogicalMap()[6][4] = 1;
        getLogicalMap()[5][4] = 1;
        getLogicalMap()[4][4] = 1;
        getLogicalMap()[3][4] = 1;
        getLogicalMap()[3][5] = 1;
        getLogicalMap()[3][6] = 1;

        placePowerUp();

        // Goal
        staticEntities.add(new Goal(this, map.getEnd()[0]*tileSize, map.getEnd()[1]*tileSize, tileSize, tileSize));

        // HUD
        headsUpDisplay = new HUD(this);

        // This starts the music yo.
        if(!DEBUG_MODE) {
            startMusic();
        }
    }

    public Player getPlayer(){
        return player;
    }

    public void startMusic(){
        try {
            music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
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
        gameCount++;
        player.incrementScore();
        restart = true;
        if(player.isPlayerBig()){
            player.adjustSize();
        }
        resetMap();
//        headsUpDisplay.announceNewGame(Integer.toString(gameCount));
    }

    private void resetMap(){
        for (StaticEntity e:staticEntities){
            e.setVisible();
        }
        placePowerUp();
    }

    public void placePowerUp(){
        Random r = new Random();
        staticEntities.add(new PowerUp(this, 1+r.nextInt((mapWidth-1)*tileSize), 1+r.nextInt((mapHeight-1)*tileSize), tileSize, tileSize));
    }

    public void moveEntity(MovableEntity e, float newX, float newY) {
        // just check x collisions keep y the same
        moveEntityInAxis(e, Axis.X, newX, e.getY());
        // just check y collisions keep x the same
        moveEntityInAxis(e, Axis.Y, e.getX(), newY);
    }

    public void moveEntityInAxis(MovableEntity e, Axis axis, float newX, float newY) {
        if(!tileCollision(e, e.getCurrentDir(), newX, newY) && !entityCollision(e, e.getCurrentDir(), newX, newY)) {
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
                if(map.getMap()[y][x] == GameMap.WALL) {
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
                    e1.entityCollision(e2, newX, newY);
                }
            }
        }

        return collision;
    }

    @Override
    public void render () {

        delta = Gdx.graphics.getDeltaTime();
        if(!Gdx.input.isKeyPressed(Input.Keys.SPACE) && tick%30 == 0){
            isSpacePressed = false;
        }


        if(!restart){
            // Only update movable entities
            for (MovableEntity e : movableEntities){
                e = e instanceof Player ? ((Player) e) : ((EnemyEntity) e);
                e.update(delta);
                moveEntity(e, e.getX() + e.getDx(), e.getY() + e.getDy());
            }
        }else{
            player.setPosition(map.getStart()[0]*tileSize, map.getStart()[1]*tileSize);
            restart = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !isSpacePressed){
            EnemyEntity.astarRun = true;
            removeAstar();
            isSpacePressed = true;
        }


        Gdx.gl.glClearColor(0.20f, 0.20f, 0.20f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        drawMap();
        headsUpDisplay.drawLabels(Integer.toString(player.getScore()));
        if(gameCount > 0)
            headsUpDisplay.announceNewGame(Integer.toString(gameCount));

        // Draw all the staticEntities.
        drawStaticEntities();
        drawMovingEntities();

        tick++;
        batch.end();
    }

    public void drawMovingEntities(){
        for (MovableEntity me: movableEntities){
            if(me instanceof Player){
                drawPlayer();
            }else{
                if(tick % 60 == 0) {
                    EnemyEntity.astarRun = true;
                    removeAstar();
                }
                batch.draw(me.getTexture(), me.getX(), me.getY());
//                System.out.println("ENEMYPOS: " + me.getX() + "," + me.getY());
//                System.out.println("PLAYERPOS: " + player.getX() + "," +  player.getY());
//                System.out.println("ENEMYTILE: " + me.getTile().getIntX() + "," +  me.getTile().getIntY());

            }
        }
    }

    public void drawStaticEntities(){
        for (StaticEntity e:staticEntities){
            if(e.isVisible())
                batch.draw(e.getTexture(), e.getX(), e.getY());
        }
    }

    public void drawPlayer(){

        if(DEBUG_MODE){
            batch.draw(player.getTexture(), player.getX(), player.getY());
        }else {

            Sprite playerSprite;
            if (tick % threshold == 0) {
                playerSprite = player.getNextSprite();
            } else playerSprite = player.getCurrentSprite();
            playerSprite.setBounds(player.getX(), player.getY(), player.getSpriteWidth(), player.getSpriteHeight());
            playerSprite.draw(batch);
        }
    }


        /* Variable music volume was a bad idea..
        if(player.isMoving() && playMusic){
            music.setVolume(0.8f);
        }else if(playMusic)
            music.setVolume(0.4f);
            }
        */

    public void removeAstar(){
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if(getLogicalMap()[y][x] == GameMap.OPEN || getLogicalMap()[y][x] == GameMap.CLOSED ){
                    getLogicalMap()[y][x] = GameMap.EMPTY;
                }
            }
        }
    }

    public void drawMap(){
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if(map.getMap()[y][x] == GameMap.WALL){
                    batch.draw(GameMap.TEX_WALL, x * tileSize, y * tileSize);
                }
                if(map.getMap()[y][x] == GameMap.OPEN){
                    batch.draw(GameMap.TEX_DEFAULT, x * tileSize, y * tileSize);
                }
                if(map.getMap()[y][x] == GameMap.CLOSED){
                    batch.draw(GameMap.TEX_DEFAULT_LIGHT, x * tileSize, y * tileSize);
                }
            }
        }
        if(DEBUG_MODE)
            batch.draw(map.getSSTexture(), map.getStart()[0]*tileSize, map.getStart()[1]*tileSize);
    }
}
