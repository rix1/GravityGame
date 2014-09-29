package org.rix1.gravity.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import org.rix1.gravity.Entites.Player;


/**
 * Created by Rikard Eide on 29/09/14.
 * Description:
 */

public class Utils {

    public static final String FILENAME = "gamestate.json";

    public Utils(){

    }

    public static void saveGame(Player player){
        Gson gson = new Gson();
        PlayerInfo p = player.getPlayerInfo();
        String json = gson.toJson(p); // Silly, yea I know, but I'll change it later on.
        FileHandle file = Gdx.files.local(FILENAME);
        file.writeString(json, false);
    }

    public static PlayerInfo loadGame() {
        PlayerInfo playerInfo;
        Gson gson = new Gson();
        FileHandle file = Gdx.files.local(FILENAME);
        if (file.exists()) {
            String json = file.readString();
            playerInfo = gson.fromJson(json, PlayerInfo.class);
        } else playerInfo = null;

        return playerInfo;
    }
}
