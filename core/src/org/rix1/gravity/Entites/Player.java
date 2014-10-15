package org.rix1.gravity.Entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.rix1.gravity.Animations.Animation;
import org.rix1.gravity.Animations.AnimationGraph;
import org.rix1.gravity.Animations.IdleAnimation;
import org.rix1.gravity.Animations.WalkAnimation;
import org.rix1.gravity.Drawable;
import org.rix1.gravity.GameClass;
import org.rix1.gravity.GameMap;
import org.rix1.gravity.Utils.Direction;
import org.rix1.gravity.Utils.PlayerInfo;
import org.rix1.gravity.Utils.Utils;

public class Player extends MovableEntity implements Drawable {

    private int score = 0;
    private AnimationGraph aniGraph = new AnimationGraph();

    private Animation walkForwardAnimation;
    private Animation walkBackwardAnimation;
    private Animation flyAnimation;
    private boolean isUpPressed = false;

    @Deprecated
    private boolean hasPowerUp;

    private float spriteWidth;
    private float spriteHeight;

    private boolean playerBig;

    public static final float SIZE_SMALL_WIDTH = 30f;
    public static final float SIZE_SMALL_HEIGHT = 50f;


    public Player(GameClass game, float x, float y, int width, int height, float speed) {
        super(game, x, y, width, height, speed);
        walkForwardAnimation = new WalkAnimation(4, 17, "playerWalk.txt", false);
        walkBackwardAnimation = new WalkAnimation(4, 17, "playerWalk.txt", true);
        flyAnimation = new WalkAnimation(1, 12, "flyy.txt", false);
        texture = GameMap.TEX_POWERUP;
        hasPowerUp = false;
        spriteWidth = SIZE_SMALL_WIDTH;
        spriteHeight = SIZE_SMALL_HEIGHT;
        playerBig = false;
    }

    public boolean isPlayerBig() {
        return playerBig;
    }

    public void adjustSize(){
        if(playerBig){
            spriteWidth = 30f;
            spriteHeight = 50f;
        }else{
            spriteWidth = 60f;
            spriteHeight = 100f;
        }
        playerBig = !playerBig;
    }

    public float getSpriteWidth() {
        return spriteWidth;
    }

    public float getSpriteHeight() {
        return spriteHeight;
    }



    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * I know this kinda defies the whole point of Gson, but before I can
     * use v2.3, I have to clean up my Player object, which I dont have time to.
     * @param info
     */

    public void setPlayerInfo(PlayerInfo info){
        this.score = info.getScore();
        this.x = info.getX();
        this.y = info.getY();
    }

    @Deprecated
    public boolean hasPowerUp() {
        return hasPowerUp;
    }

    @Deprecated
    public void setHasPowerUp(boolean hasPowerUp) {
        this.hasPowerUp = hasPowerUp;
    }

    public PlayerInfo getPlayerInfo(){
        return new PlayerInfo(x, y, score);
    }


    public void incrementScore(){
        score++;
    }

    public int getScore() {
        return score;
    }

    public void incrementSpeed(){
        speed+=5;
    }

    public void update(float delta){
        dx = 0;
        dy = 0;


        if(!Gdx.input.isKeyPressed(Input.Keys.UP)){
            isUpPressed = false;
        }


        // move
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy = speed * delta;
            currentDir = Direction.U;
            isUpPressed = true;
        }

        if(this.getY() > Utils.tileSize && !isUpPressed) {
            dy = -speed * delta;
            currentDir = Direction.D;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy = -speed * delta;
            currentDir = Direction.D;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx = -speed * delta;
            currentDir = Direction.L;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx = speed * delta;
            currentDir = Direction.R;
        }

        setMoving();
        if(isMoving)
            queueAnimation();
        else{
            removeAnimaton();
        }
    }

    @Override
    public void entityCollision(Entity e2, float newX, float newY) {
        // Whats best? To make the collider or the collidee handle collision? I say both.
//        System.out.println("Player collision around: " + newX + " " + newY);
        e2.entityCollision(this, newX, newY);
        e2 = e2 instanceof StaticEntity ? ((StaticEntity) e2) : e2;

        if(((StaticEntity) e2).inBackground){
            move(newX, newY);
            if(e2 instanceof PowerUp) {
                game.getStaticEntities().remove(e2);
                if(!isPlayerBig())
                    adjustSize();
            }
        }else {
            System.out.println("\"Hey Jack! We just hit a wall or something\"");
        }
    }

    private void removeAnimaton(){
        aniGraph.remove();
    }

    private void queueAnimation(){

        switch (currentDir){
            case U:
                aniGraph.add(flyAnimation);
//                System.out.println("moving up or down!");
                break;
            case D:
//                if(y > 2*game.getTileSize()) {
//                    aniGraph.add(flyAnimation);
//                    System.out.println("MOVING down!");
//                }
                break;
            case L:
                if(y > 2*game.getTileSize()) {
                    aniGraph.add(flyAnimation);
                }
                else aniGraph.add(walkBackwardAnimation);
                break;
            case R:
                if(y > 2*game.getTileSize()) {
                    aniGraph.add(flyAnimation);
                }
                else aniGraph.add(walkForwardAnimation);
                break;
        }
    }


    @Override
    public Sprite getNextSprite() {
        return aniGraph.getNext();
    }

    @Override
    public Sprite getCurrentSprite() {
        return aniGraph.getCurrentSprite();
    }
}