package org.rix1.gravity.Utils;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */
public class PlayerInfo {

    private float x;
    private float y;
    private int score;

    public PlayerInfo(float x, float y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public PlayerInfo getPosition(){
        return this;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
