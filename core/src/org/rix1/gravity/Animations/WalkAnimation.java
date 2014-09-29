package org.rix1.gravity.Animations;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */
public class WalkAnimation extends Animation{

    boolean playReverse;

    public WalkAnimation(int start, int end, String spriteSheetName, boolean playReverse) {
        super(start, end, spriteSheetName);
        this.playReverse = playReverse;
    }

    /**
     * At the moment, this method needs to be called once a tick.
     * @return
     */

    public Sprite posAnimation(){
        Sprite retSprite;

        if(posCounter >= end){
            posCounter = start;
            return null;
        }
        retSprite = sprites.get(posCounter);
        posCounter++;
        return retSprite;
    }

    public Sprite negAnimation(){
        Sprite retSprite;

        if(negCounter <= start){
            negCounter = end-1;
            return null;
        }
        retSprite = sprites.get(negCounter);
        negCounter--;
        return retSprite;
    }

    @Override
    public Sprite animate() {
        if(playReverse){
            return negAnimation();
        }else return posAnimation();
    }
}





/* ============================================================

            switch (currentDir) {
                case U:
                    if (isMoving()) {
                        sprites.get(1).setPosition(x, y);
                        retSprite = sprites.get(1);
                    } else {
                        sprites.get(1).setPosition(x, y);
                        retSprite = sprites.get(1);
                    }
                    break;

                case D:
                    if (isMoving()) {
                        sprites.get(0).setPosition(x, y);
                        retSprite = sprites.get(0);

                    } else {
                        sprites.get(0).setPosition(x, y);
                        retSprite = sprites.get(0);
                    }
                    break;
                case L:
                    if (isMoving()) {
                        sprites.get(negCounter).setPosition(x, y);
                        retSprite = sprites.get(negCounter);
                    } else {
                        negCounter = sprites.size - 1;
                        sprites.get(0).setPosition(x, y);
                        retSprite = sprites.get(0);
                    }
                    break;
                case R:
                    if (isMoving()) {
                        sprites.get(posCounter).setPosition(x, y);
                        retSprite = sprites.get(posCounter);
                    } else {
                        posCounter = 0;
                        sprites.get(0).setPosition(x, y);
                        retSprite = sprites.get(0);
                    }
                    break;
                default:
                    // set start stop for up
                    break;
            }
*/