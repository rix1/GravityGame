package org.rix1.gravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class Player extends MovableEntity implements Drawable {

    private String spriteSheetName;
    private Array<Sprite> sprites;
    private TextureAtlas spriteSheet;
    private AssetManager assets;
    private Sprite currentSprite;

    private int score = 0;

    private int posCounter = 0;
    private int negCounter = 0;
    private int start = 4;  // WARNING: Not accounted for other spritesheets
    private int end = 0;    // WARNING: Not accounted for other spritesheets

    public Player(MyGdxGame game, float x, float y, int width, int height, float speed, String spriteSheetName) {
        super(game, x, y, width, height, speed);

        this.spriteSheetName = spriteSheetName;
        setSprite();

    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void incrementScore(){
        score++;
    }

    public int getScore() {
        return score;
    }

    public void setSprite(){
        assets = new AssetManager();
        assets.load(spriteSheetName, TextureAtlas.class);

        assets.finishLoading();
        spriteSheet = assets.get(spriteSheetName);
        sprites = spriteSheet.createSprites();
        end = sprites.size;
    }

    public Array<Sprite> getSprites() {
        return sprites;
    }

    public void update(float delta){

        dx = 0;
        dy = 0;

        // move
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy = speed * delta;
            currentDir = MyGdxGame.Direction.U;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy = -speed * delta;
            currentDir = MyGdxGame.Direction.D;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx = -speed * delta;
            currentDir = MyGdxGame.Direction.L;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx = speed * delta;
            currentDir = MyGdxGame.Direction.R;
        }

        setMoving();
    }

    @Override
    public void entityCollision(Entity e2, float newX, float newY, MyGdxGame.Direction direction) {
        // Whats best? To make the collider or the collidee handle collision? I say both.
        System.out.println("Player collision around: " + newX + " " + newY);
        e2.entityCollision(this, newX, newY, direction);
        e2 = e2 instanceof StaticEntity ? ((StaticEntity) e2) : e2;

        if(((StaticEntity) e2).isPickupable){
            move(newX, newY);
        }else {
            System.out.println("\"Hey Jack! We just hit a wall or something\"");
        }
    }


    @Override
    public Sprite getNextSprite() {
        // TODO: Handle queue of sprites to be drawn
        // This method should return the text sprite to be drawn.
        // this is ideally the
        // Desperately trying to implement a motion graph...........

        Sprite retSprite = null;

        if (posCounter >= sprites.size)
            posCounter = start;
        if (negCounter <= start)
            negCounter = end - 1;

        switch (currentDir) {
            case U:
                if (isMoving()) {
                    sprites.get(1).setPosition(x, y);
                    retSprite = sprites.get(1);
                } else {
                    sprites.get(1).setPosition(x, y);
                    retSprite = sprites.get(1);
                }
                break;

            case D:
                if (isMoving()) {
                    sprites.get(0).setPosition(x, y);
                    retSprite = sprites.get(0);

                } else {
                    sprites.get(0).setPosition(x, y);
                    retSprite = sprites.get(0);
                }
                break;
            case L:
                if (isMoving()) {
                    sprites.get(negCounter).setPosition(x, y);
                    retSprite = sprites.get(negCounter);
                } else {
                    negCounter = sprites.size - 1;
                    sprites.get(0).setPosition(x, y);
                    retSprite = sprites.get(0);
                }
                break;
            case R:
                if (isMoving()) {
                    sprites.get(posCounter).setPosition(x, y);
                    retSprite = sprites.get(posCounter);
                } else {
                    posCounter = 0;
                    sprites.get(0).setPosition(x, y);
                    retSprite = sprites.get(0);
                }
                break;
            default:
                // set start stop for up
                break;
        }

        posCounter++;
        negCounter--;

        currentSprite = retSprite;
        return retSprite;
    }

    @Override
    public Sprite getCurrentSprite() {
        return currentSprite;
    }
}