package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description: Object for the endgoal. We don't need one for the start, because
 * we dont want to check for collisions with the start.
 */

public class Goal extends StaticEntity {

    public Goal(MyGdxGame game, float x, float y, int width, int height, Texture texture) {
        super(game, x, y, width, height, texture);
        isPickupable = true;
    }

    public void entityCollision(Entity e2, float newX, float newY, MyGdxGame.Direction direction) {
        System.out.println("Reached GOAL around: " + newX + " " + newY);

        if(e2 instanceof Player){
            game.restart();
        }
    }

    @Override
    public void update(float delta) {

    }
}
