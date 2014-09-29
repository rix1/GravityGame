package org.rix1.gravity.Animations;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */

public abstract class Animation {

    protected int posCounter = 0;
    protected int negCounter = 0;

    protected int start;
    protected int end;

    protected String spriteSheetName;
    protected Array<Sprite> sprites;


    protected Animation(int start, int end, String spriteSheetName) {
        this.start = start;
        this.end = end;
        this.spriteSheetName = spriteSheetName;
        posCounter = start;
        negCounter = end-1;
        setSprite();
    }

    public void setSprite(){
        AssetManager assets = new AssetManager();
        assets.load(spriteSheetName, TextureAtlas.class);
        assets.finishLoading();

        TextureAtlas spriteSheet = assets.get(spriteSheetName);
        sprites = spriteSheet.createSprites();
    }

    public abstract Sprite animate();

}
