package org.rix1.gravity.Entites;

import com.badlogic.gdx.graphics.Texture;
import org.rix1.gravity.GameClass;
import org.rix1.gravity.Utils.Direction;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description: Object for the endgoal. We don't need one for the start, because
 * we dont want to check for collisions with the start.
 */

public class Goal extends StaticEntity {

    public Goal(GameClass game, float x, float y, int width, int height, Texture texture) {
        super(game, x, y, width, height, texture);
        isPickupable = true;
    }

    public void entityCollision(Entity e2, float newX, float newY, Direction direction) {
        System.out.println("Reached GOAL around: " + newX + " " + newY);

        if(e2 instanceof Player){
            game.restart();
        }
    }

    @Override
    public void update(float delta) {

    }
}
