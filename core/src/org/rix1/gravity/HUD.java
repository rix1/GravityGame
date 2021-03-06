package org.rix1.gravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Rikard Eide on 29/09/14.
 * Description: Heads up display to show score etc on top
 */

public class HUD {

    private BitmapFont font;
    private GameClass game;

    public HUD(GameClass game){
        this.game = game;
        generateBitmapFont();
    }

    private void generateBitmapFont(){

        FreeTypeFontGenerator generator = null;
        try {
            generator = new FreeTypeFontGenerator(Gdx.files.internal("NotoSerif-BoldItalic.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 25;
            font = generator.generateFont(parameter);
            generator.dispose();
        } catch (RuntimeException e) {
            System.out.println("Custom font not found, using default font...");
            font = new BitmapFont();
        }
        font.setColor(Color.WHITE);
    }

    public void drawLabels(String score){
        font.draw(game.batch, " SCORE: " + score, 20,370);
    }

    public void announceNewGame(String gameCount){

//        font.draw(game.batch, "YAY, U FOUND EXIT, THIS IS GAME NO. " + gameCount, 250,370);
    }

}
