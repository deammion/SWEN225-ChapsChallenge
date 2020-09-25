package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import javax.json.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadJSON {

    /**
     * User selects the chosen replay, this method converts that json file to a Arraylist of Strings
     * to be read by the Levels/Maze class and converted to a display
     * as the directory is already set, only the file name is required to load file
     *
     * @param fileName - filename in directory chosen for replay
     * @return gameState - ArrayList consisting of Strings that can be read into a display
     */
    public static ArrayList<String> loadGameStates(String fileName){
        ArrayList<String> gameStates = new ArrayList<>();
        try {
            String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
            FileReader fr = new FileReader(new File(dir + fileName));

            JsonReader jsonParser = Json.createReader(fr);
            JsonArray jsonArray = jsonParser.readArray();

            for(int i =0; i < jsonArray.size(); i++) { //iterate through Json array
                JsonObject jsonObj = jsonArray.get(i).asJsonObject(); //convert to Json object
                JsonString jsonString = (JsonString) jsonObj.getValue("/gameState " + i); //convert gameState Json Object to string
                String gameState = jsonString.getString();
                gameStates.add(gameState);
            }

            jsonParser.close();
            fr.close();
        } catch (IOException | JsonException e) {
            e.printStackTrace();
            return null;
        }
        return gameStates; // return Arraylist of gameState Strings
    }
}