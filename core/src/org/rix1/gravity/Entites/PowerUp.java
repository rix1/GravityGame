package org.rix1.gravity.Entites;

import com.badlogic.gdx.graphics.Texture;
import org.rix1.gravity.MyGdxGame;

/**
 * Created by Rikard Eide on 28/09/14.
 * Description:
 */
public class PowerUp extends StaticEntity {

    public PowerUp(MyGdxGame game, float x, float y, int width, int height, Texture texture) {
        super(game, x, y, width, height, texture);
        isPickupable = true;
        inBackground = true;
    }
}
