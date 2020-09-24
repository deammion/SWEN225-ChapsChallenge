package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import javax.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadJSON {

    private final String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
    private FileReader fr;



    public loadJSON(String fileDir) {
        loadGameStates(fileDir);
    }

    public ArrayList<String> loadGameStates(String fileDir){
        ArrayList<String> gameStates = new ArrayList<>();
        try {
            fr = new FileReader(new File(dir + fileDir));

            JsonReader jsonParser = Json.createReader(fr);
            JsonArray jsonArray = jsonParser.readArray();

            for(int i =0; i < jsonArray.size(); i++) {
                JsonObject jsonObj = jsonArray.get(i).asJsonObject();
                JsonString jsonString = (JsonString) jsonObj.getValue("/maze " +i);
                String gameState = jsonString.getString();
                gameStates.add(gameState);
            }

            jsonParser.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JsonException e) {
            e.printStackTrace();
            return null;
        }
        return gameStates;
    }
}