package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rikard Eide on 27/09/14.
 * Description:
 */

public class GameMap {

    private int width;
    private int height;
    private Texture startStopTexture;
    private Texture tileTexture;
    private boolean [][] map; // [y][x]
    private float[] start; // x,y
    private float[] end;    // x,y

    public GameMap(int height, int width){
        this.height = height;
        this.width = width;
        generateMap();

        tileTexture = new Texture("block.png");
        startStopTexture = new Texture("startend.png");

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
        return startStopTexture;
    }

    public Texture getTileTexture() {
        return tileTexture;
    }

    private void generateMap(){
        map = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(y == 0 || y == height-1 || x == 0 || x == width-1){
                    map[y][x] = true;
                }
            }
        }
    }

    public boolean[][] getMap() {
        return map;
    }
}
