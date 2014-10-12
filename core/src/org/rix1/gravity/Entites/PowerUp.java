package org.rix1.gravity.Entites;

import com.badlogic.gdx.graphics.Texture;
import org.rix1.gravity.GameClass;
import org.rix1.gravity.GameMap;

/**
 * Created by Rikard Eide on 28/09/14.
 * Description:
 */
public class PowerUp extends StaticEntity {

    public PowerUp(GameClass game, float x, float y, int width, int height) {
        super(game, x, y, width, height);
        texture = GameMap.TEX_POWERUP;
        isPickupable = true;
        inBackground = true;
    }
}
