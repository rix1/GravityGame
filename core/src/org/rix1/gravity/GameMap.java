package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rikard Eide on 27/09/14.
 * Description:
 */


public class GameMap {

    private int width;
    private int height;
    private Texture background;
    private boolean [][] map; // [y][x]

    public GameMap(int height, int width){
        this.height = height;
        this.width = width;
        generateMap();
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
