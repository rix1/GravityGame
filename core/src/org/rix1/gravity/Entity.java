package org.rix1.gravity;

import com.badlogic.gdx.graphics.Texture;
import org.rix1.gravity.MyGdxGame.Direction;

public abstract class Entity {
    protected MyGdxGame game;
    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected int width;
    protected int height;

    public Entity(MyGdxGame game, float x, float y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public void update(float delta) {
	}

	public void move(float newX, float newY) {
		x = newX;
		y = newY;
	}

	public void render() {

	}

	public void tileCollision(int tileX, int tileY, float newX, float newY, Direction direction) {
		System.out.println("tile collision at: " + tileX + " " + tileY);

		if(direction == Direction.U) {
			y = tileY * game.tileSize + game.tileSize;
		}
		else if(direction == Direction.D) {
			y = tileY * game.tileSize - height;
		}
		else if(direction == Direction.L) {
			x = tileX * game.tileSize + game.tileSize;
		}
		else if(direction == Direction.R) {
			x = tileX * game.tileSize - width;
		}
	}

	public void entityCollision(Entity e2, float newX, float newY, Direction direction) {
		System.out.println("entity collision around: " + newX + " " + newY);

		move(newX, newY);
		// could also resolve entity collisions in the same we do tile collision resolution
		// as shown in class
	}
}
