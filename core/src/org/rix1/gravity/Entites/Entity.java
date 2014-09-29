package org.rix1.gravity.Entites;

import org.rix1.gravity.GameClass;
import org.rix1.gravity.Utils.Direction;

public abstract class Entity {
    protected GameClass game;
    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected int width;
    protected int height;
    protected boolean inBackground;
    protected boolean isVisible;

    public Entity(GameClass game, float x, float y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isVisible = true; // By default every object should be visible, right?
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void setVisible(){
        isVisible = true;
    }

    public abstract void update(float delta);

	public void move(float newX, float newY) {
        x = newX;
		y = newY;
	}

	public void tileCollision(int tileX, int tileY, float newX, float newY, Direction direction) {
        System.out.println("tile collision at: " + tileX + " " + tileY);

        if (direction == Direction.U) {
            y = tileY * game.getTileSize() + game.getTileSize();
        } else if (direction == Direction.D) {
            y = tileY * game.getTileSize() - height;
        } else if (direction == Direction.L) {
            x = tileX * game.getTileSize() + game.getTileSize();
        } else if (direction == Direction.R) {
            x = tileX * game.getTileSize() - width;
        }
    }
	public abstract void entityCollision(Entity e2, float newX, float newY, Direction direction);

    //====== Getters and setters. ====== //

    // Some of these should be moved down the package hirearchy

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
