package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rikard Eide on 28/09/14.
 * Description: These staticEntities should not be moving and some should afford to be picked up
 */


public class StaticEntity extends Entity {

    private Texture texture;

    public StaticEntity(MyGdxGame game, float x, float y, int width, int height, Texture texture) {
        super(game, x, y, width, height);
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
