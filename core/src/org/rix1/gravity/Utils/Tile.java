package org.rix1.gravity.Utils;

/**
 * Created by Rikard Eide on 09/10/14.
 * Description: Tile object
 */

public class Tile implements Comparable<Tile>{

    private int x;
    private int y;

    public Tile(float x, float y) {
        this.x = (int) x/Utils.tileSize;
        this.y = (int) y/Utils.tileSize;
    }

    public Tile(int x , int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Should compare tiles. Be careful to use this to compare positions, as it only compare tiles.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */

    @Override
    public int compareTo(Tile otherPos) {
        int x = (getX() - otherPos.getX()); // Negative, zero or positive
        int y = (getY() - otherPos.getY()); // Negative, zero or positive

        if((x == 0) && (y == 0))
            return 0;
        else if(x < 0 || y < 0)
            return -1;

        else return 1;
    }
}
