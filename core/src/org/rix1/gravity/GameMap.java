package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rikard Eide on 27/09/14.
 * Description:
 */

public class GameMap {

    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int PLAYER = 2;
    public static final int POWERUP = 3;
    public static final int MUD = 4;

    public static final int OPEN = 5;
    public static final int CLOSED = 6;



    public static final Texture TEX_STARTGOAL = new Texture("startend.png");
    public static final Texture TEX_WALL = new Texture("block.png");
    public static final Texture TEX_ENEMY = new Texture("enemy.png");
    public static final Texture TEX_POWERUP = new Texture("powerup.png");
    public static final Texture TEX_DEFAULT = new Texture("default.png");
    public static final Texture TEX_DEFAULT_LIGHT = new Texture("defaultLight.png");

    private int width;
    private int height;

    private int[][] map; // [y][x]
    private float[] start; // x,y
    private float[] end;    // x,y

    public GameMap(int height, int width){
        this.height = height;
        this.width = width;
        generateMap();

        // It should be the map's responsibility to hold start/goal positions
        start = new float[]{1,1};
        end = new float[]{width-2,1}; // Minus two because
    }

    public float[] getStart() {
        return start;
    }

    public float[] getEnd() {
        return end;
    }

    public Texture getSSTexture() {
        return TEX_STARTGOAL;
    }

    public Texture getTileTexture() {
        return TEX_WALL;
    }

    private void generateMap(){
        map = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(y == 0 || y == height-1 || x == 0 || x == width-1){
                    map[y][x] = WALL;
                }
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

}
