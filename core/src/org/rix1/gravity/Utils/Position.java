package org.rix1.gravity.Utils;

/**
 * Created by Rikard Eide on 09/10/14.
 * Description: Position object
 */

public class Position implements Comparable{

    private float x;
    private float y;



    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getIntX(){
        return (int)x;
    }
    public int getIntY(){
        return (int)y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
