package org.rix1.gravity.Animations;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */
public class IdleAnimation extends Animation {

    protected IdleAnimation(int start, int end, String spriteSheetName) {
        super(start, end, spriteSheetName);
    }

    @Override
    public Sprite animate() {
        Sprite retSprite;

        if (posCounter >= end)
            posCounter = start;

        retSprite = sprites.get(posCounter);
        posCounter++;
        return retSprite;
    }
}
