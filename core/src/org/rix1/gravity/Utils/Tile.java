package org.rix1.gravity.Utils;

/**
 * Created by Rikard Eide on 09/10/14.
 * Description: Tile object
 */

public class Tile implements Comparable<Tile>{

    private int intX;
    private int intY;

    public Tile(float x, float y) {
        this.intX = (int) x /Utils.tileSize;
        this.intY = (int) y /Utils.tileSize;
    }

    public Tile(int intX, int intY){
        this.intX = intX;
        this.intY = intY;
    }

    public int getIntX() {
        return intX;
    }

    public int getIntY() {
        return intY;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "intX=" + intX +
                ", intY=" + intY +
                '}';
    }

    /**
     * Should compare tiles. Be careful to use this to compare positions, as it only compare tiles.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */

    @Override
    public int compareTo(Tile otherPos) {
        int x = (getIntX() - otherPos.getIntX()); // Negative, zero or positive
        int y = (getIntY() - otherPos.getIntY()); // Negative, zero or positive

        if((x == 0) && (y == 0))
            return 0;
        else if(x < 0 || y < 0)
            return -1;

        else return 1;
    }
}
