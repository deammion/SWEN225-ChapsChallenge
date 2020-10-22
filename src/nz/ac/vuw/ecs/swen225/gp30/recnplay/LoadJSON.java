package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;

/**
 * LoadJSON class is responsible for parsing the JSON file and returning the moves as an arraylist
 * and the level number as an int to the Replay class.
 *
 * @author tamasedami
 */
public class LoadJSON {

    private final static String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";

    /**
     * User selects the chosen replay, this method converts that json file to a Arraylist of Strings
     * which the record class can convert into moves and time. in order to return the correct move
     * at the correct time during a reply
     *
     * @param fileName - filename in directory chosen for replay
     * @return playerMoves - ArrayList consisting of Strings containing a char for the move and an int for the time
     */
    public ArrayList<String> loadPlayerMoves(String fileName){
        ArrayList<String> playerMoves = new ArrayList<>();
        try {
            FileReader fr = new FileReader(dir + fileName);

            JsonReader jsonReader = Json.createReader(fr);
            JsonArray jsonArray = jsonReader.readArray();

            for(int i =0; i < jsonArray.size()-1; i++) { //iterate through Json array
                JsonObject jsonObj = jsonArray.get(i).asJsonObject(); //convert to Json object
                JsonString jsonStringPlayer = (JsonString) jsonObj.getValue("/Player" + i); //converts move Json Object to string
                playerMoves.add(jsonStringPlayer.toString());
            }

            jsonReader.close();
            fr.close();
        } catch (IOException | JsonException e) {
            e.printStackTrace();
            return null;
        }
        return playerMoves; // return Arraylist of player moves as strings
    }

    /**
     * finds the level object in the json file in order to load the correct level for the replay
     *
     * @param fileName - filename in directory chosen for replay
     * @return level - an int use to differ between levels for the replay
     */
    public Integer loadLevel(String fileName) {
        int level = 0; //initialise as 0
        try {
            FileReader fr = new FileReader(new File(dir + fileName));

            JsonReader jsonParser = Json.createReader(fr);
            JsonArray jsonArray = jsonParser.readArray();

            JsonObject jsonObj = jsonArray.get(jsonArray.size()-1).asJsonObject(); //convert to Json object
            JsonString jsonStringLevel = (JsonString) jsonObj.getValue("/Level"); //convert move Json Object to string
            level = Integer.parseInt(jsonStringLevel.getString());

            jsonParser.close();
            fr.close();
        } catch (IOException | JsonException e) {
            e.printStackTrace();
            return null;
        }
        return level; // return the level number as a Integer
    }
}
