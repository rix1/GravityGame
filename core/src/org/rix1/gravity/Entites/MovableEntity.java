package org.rix1.gravity.Entites;

import com.badlogic.gdx.graphics.Texture;
import org.rix1.gravity.GameClass;
import org.rix1.gravity.GameMap;
import org.rix1.gravity.Utils.Direction;

/**
 * Created by Rikard Eide on 28/09/14.
 * Description:
 */

public class MovableEntity extends Entity {

    protected Direction currentDir;
    protected boolean isMoving;
    protected float speed;
    protected Texture texture;


    public MovableEntity(GameClass game, float x, float y, int width, int height, float speed) {
        super(game, x, y, width, height);
        this.speed = speed;
        currentDir = Direction.L; // Default direction
        inBackground = false;
        this.texture = GameMap.TEX_DEFAULT;

    }


    public void tileCollision(int tileX, int tileY, float newX, float newY, Direction direction) {

        if (direction == Direction.U) {
//            System.out.println("Collision detected: UP");
            dy = speed;
        } else if (direction == Direction.D) {
//            System.out.println("Collision detected: DOWN");
            dy = -speed;
        } else if (direction == Direction.L) {
//            System.out.println("Collision detected: LEFT");
            dx = -speed;
        } else if (direction == Direction.R) {
//            System.out.println("Collision detected: RIGHT");
            dx = speed;
        }
    }



        public Texture getTexture() {
        return texture;
    }

    public Direction getCurrentDir(){
        return currentDir;
    }

    public boolean isMoving(){
        return isMoving;
    }

    protected void setMoving(){
        isMoving = (x - dx) != x || (y - dy) != y;
    }


    @Override
    public void update(float delta) {
        System.out.println("Updating MOVABLE OBJECT");
        // TODO: Create update method for bots based on some heuristic
    }

    @Override
    public void entityCollision(Entity e2, float newX, float newY) {
        System.out.println("Player collision around: " + newX + " " + newY);

    }
}
