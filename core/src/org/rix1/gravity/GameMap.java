package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rikard Eide on 27/09/14.
 * Description:
 */

public class GameMap {


    public static final int WEIGHT_LIGHT = 1;
    public static final int WEIGHT_DEFAULT = 2;
    public static final int WEIGHT_HEAVY = 20;

    public static final int EMPTY = 0;

    public static final int WALL_DEFAULT = 1;

    public static final int PLAYER = 2;
    public static final int POWERUP = 3;

    public static final int OPEN = 5;
    public static final int CLOSED = 6;

    public static final int WALL_SIDE = 8;
    public static final int WALL_DOWN = 9;

    public static final Texture TEX_STARTGOAL = new Texture("goal.png");


    public static final Texture TEX_WALL_SIDE = new Texture("wall_leftright.png");
    public static final Texture TEX_WALL_DOWN = new Texture("wall_down.png");
    public static final Texture TEX_WALL_DEFAULT = new Texture("wall_default.png");

    public static final Texture TEX_ENEMY = new Texture("enemy.png");
    public static final Texture TEX_POWERUP = new Texture("powerup.png");
    public static final Texture TEX_DEFAULT = new Texture("default.png");
    public static final Texture TEX_DEFAULT_LIGHT = new Texture("defaultLight.png");

    public static final Texture TEX_BG = new Texture("bg.png");

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

    public boolean isWall(int x, int y){
        return (map[y][x] == WALL_DEFAULT || map[y][x] == WALL_SIDE || map[y][x] == WALL_DOWN);
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

    private void generateMap(){
        map = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(y == 0 || x == 0 || x == width-1)
                    map[y][x] = WALL_DOWN;
                if(y == height-1)
                    map[y][x] = WALL_DEFAULT;
            }
        }
    }

    public String mapToString(){
        String printstring = "";
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                printstring += " " + map[y][x];

            }
            printstring += "\n";
        }
        return printstring;
    }

    public int[][] getMap() {
        return map;
    }

}
