package org.rix1.gravity.Entites;

import com.badlogic.gdx.graphics.Texture;
import org.rix1.gravity.GameClass;
import org.rix1.gravity.Utils.Direction;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */
public class EnemyEntity extends MovableEntity{

    private Texture texture;



    public EnemyEntity(GameClass game, float x, float y, int width, int height, float speed, Texture texture) {
        super(game, x, y, width, height, speed);
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void update(float delta) {
//        System.out.println("Updating MOVABLE OBJECT");
        Player player = game.getPlayer();

        dx = 0;
        dy = 0;

        if (player.hasPowerUp()) {

            // move
            if (player.getY() > this.y) {
                dy = speed * delta;
                currentDir = Direction.U;
            }
            if (player.getY() < this.y) {
                dy = -speed * delta;
                currentDir = Direction.D;
            }
            if (player.getX() < this.x) {
                dx = -speed * delta;
                currentDir = Direction.L;
            }
            if (player.getX() > this.x) {
                dx = speed * delta;
                currentDir = Direction.R;
            }
        }
    }

}
