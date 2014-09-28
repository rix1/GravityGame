package org.rix1.gravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import org.rix1.gravity.MyGdxGame.Direction;

public class Player extends MovableEntity {

    private String spriteSheetName;
    private Array<Sprite> sprites;
    private TextureAtlas spriteSheet;
    private AssetManager assets;

    public Player(MyGdxGame game, float x, float y, int width, int height, float speed, String spriteSheetName) {
        super(game, x, y, width, height, speed);

        this.spriteSheetName = spriteSheetName;
        setSprite();
    }

    public void setSprite(){
        assets = new AssetManager();
        assets.load(spriteSheetName, TextureAtlas.class);

        assets.finishLoading();
        spriteSheet = assets.get(spriteSheetName);
        sprites = spriteSheet.createSprites();
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


}