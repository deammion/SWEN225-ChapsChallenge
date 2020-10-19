package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;

public class LoadJSON {

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
            String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
            FileReader fr = new FileReader(dir + fileName);

            JsonReader jsonReader = Json.createReader(fr);
            JsonArray jsonArray = jsonReader.readArray();

            for(int i =0; i < jsonArray.size(); i++) { //iterate through Json array
                JsonObject jsonObj = jsonArray.get(i).asJsonObject(); //convert to Json object
                JsonString jsonStringPlayer = (JsonString) jsonObj.getValue("/Player" + i); //converts move Json Object to string
                String playerAction = jsonStringPlayer.getString();
                playerMoves.add(playerAction);
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
     *
     *
     * @param fileName
     * @return
     */
    public ArrayList<String> loadActorMoves(String fileName){
        ArrayList<String> actorMoves = new ArrayList<>();
        try {
            String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
            FileReader fr = new FileReader(new File(dir + fileName));

            JsonReader jsonParser = Json.createReader(fr);
            JsonArray jsonArray = jsonParser.readArray();

            for(int i =0; i < jsonArray.size(); i++) { //iterate through Json array
                JsonObject jsonObj = jsonArray.get(i).asJsonObject(); //convert to Json object
                JsonString jsonStringActor = (JsonString) jsonObj.getValue("/Actor" + i); //convert move Json Object to string
                String actorAction = jsonStringActor.getString();
                actorMoves.add(actorAction);
            }

            jsonParser.close();
            fr.close();
        } catch (IOException | JsonException e) {
            e.printStackTrace();
            return null;
        }
        return actorMoves; // return Arraylist of actor moves as strings
    }

    /**
     * finds the level object in the json file in order to load the correct level for the replay
     *
     * @param fileName - filename in directory chosen for replay
     * @return level - an int use to differ between levels for the replay
     */
    public Integer loadLevel(String fileName) {
        int level = 0;
        try {
            String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
            FileReader fr = new FileReader(new File(dir + fileName));

            JsonReader jsonParser = Json.createReader(fr);
            JsonArray jsonArray = jsonParser.readArray();

            for(int i =0; i < jsonArray.size(); i++) { //iterate through Json array
                JsonObject jsonObj = jsonArray.get(i).asJsonObject(); //convert to Json object
                JsonString jsonStringLevel = (JsonString) jsonObj.getValue("/Level"); //convert move Json Object to string
                level = Integer.parseInt(jsonStringLevel.getString());
            }

            jsonParser.close();
            fr.close();
        } catch (IOException | JsonException e) {
            e.printStackTrace();
            return null;
        }
        return level; // return the level number as a Integer
    }
}
