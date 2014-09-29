package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rikard Eide on 28/09/14.
 * Description:
 */
public class PowerUp extends StaticEntity {

    public PowerUp(MyGdxGame game, float x, float y, int width, int height, Texture texture) {
        super(game, x, y, width, height, texture);
        isPickupable = true;
    }
}
