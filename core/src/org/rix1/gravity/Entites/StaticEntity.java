package org.rix1.gravity.Entites;

import com.badlogic.gdx.graphics.Texture;
import org.rix1.gravity.GameClass;
import org.rix1.gravity.GameMap;

/**
 * Created by Rikard Eide on 28/09/14.
 * Description: These staticEntities should not be moving and some should afford to be picked up
 */


public class StaticEntity extends Entity {

    protected Texture texture;
    protected boolean isPickupable;

    public StaticEntity(GameClass game, float x, float y, int width, int height) {
        super(game, x, y, width, height);
        this.texture = GameMap.TEX_WALL;
        inBackground = false;

    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void entityCollision(Entity e2, float newX, float newY) {
        if(this.isPickupable){
            if(isVisible){
                ((Player)e2).incrementScore();
                ((Player)e2).incrementScore();
                ((Player)e2).setHasPowerUp(true);
            }
            isVisible = false;
            //TODO: Add some points or something to player;
        }
    }
}
