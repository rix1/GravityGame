package org.rix1.gravity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Rikard Eide on 28/09/14.
 * Description:
 */

public class MovableEntity extends Entity {

    protected MyGdxGame.Direction currentDir;
    protected boolean isMoving;
    protected float speed;



    public MovableEntity(MyGdxGame game, float x, float y, int width, int height, float speed) {
        super(game, x, y, width, height);
        this.speed = speed;
        currentDir = MyGdxGame.Direction.L; // Default direction
    }



    public MyGdxGame.Direction getCurrentDir(){
        return currentDir;
    }

    public boolean isMoving(){
        return isMoving;
    }

    protected void setMoving(){
        if((x-dx)!= x || (y-dy) != y){
            isMoving = true;
        }else isMoving = false;
    }

    @Override
    public void update(float delta) {
    }

}
