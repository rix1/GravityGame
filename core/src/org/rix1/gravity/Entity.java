package org.rix1.gravity;

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

    public abstract void update(float delta);

	public void move(float newX, float newY) {
		x = newX;
		y = newY;
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

	public abstract void entityCollision(Entity e2, float newX, float newY, Direction direction);
}
