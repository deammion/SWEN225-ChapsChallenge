package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import nz.ac.vuw.ecs.swen225.gp30.Move;

import javax.json.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Record class stores all moves passed from the application class.
 * converts them to JSON objects to be stored in an JSON array which
 * is then saved as a JSON file
 *
 * @author tamasedami
 */
public class Record {

    //initialises for directory, and standard naming for files
    private static final String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
    private static final String fileNamePrefix = "record-";
    private static final String fileNameSuffix = ".json";

    //initialises ints required for saving gameState and file iterations
    private int saveIteration = 1;
    private int playerMoveIndex = 0;

    //initialises Json array builder, file name and file in order to save gameState in an array and write to file
    private final JsonArrayBuilder arrayBuilder;
    private String fileName;
    private final File file;

    /**
     * constructor
     */
    public Record() {
        arrayBuilder = Json.createArrayBuilder(); //initialise new json array builder
        fileName = fileNamePrefix + saveIteration + fileNameSuffix;
        file = new File(dir + fileName); //initialise new file
    }

    /**
     * called by chapsChallenge, saves the move which is converted to a string, includes the time remaining
     * done this way to limit file size
     *
     * @param move - Move functions used by application to move actors
     * @param time - time remaining in level
     */
    public void storePlayerMove(Move move,int time) {
        JsonObjectBuilder playerMoveObject = Json.createObjectBuilder().add("Player" + playerMoveIndex++,convertMoveToString(move) + time);
        arrayBuilder.add(playerMoveObject);

    }

    /**
     * stores the level number, so correct level info can be loaded for replay
     *
     * @param levelNum -Integer dictating the level number
     */
    public void storeLevel(Integer levelNum) {
        String levelString = Integer.toString(levelNum); //converts int to string for ease of reading file
        JsonObjectBuilder gameStateObj = Json.createObjectBuilder().add("Level", levelString); //converts level to a JsonObject
        arrayBuilder.add(gameStateObj); //adds JsonObject to JsonArray
    }

    /**
     * converts the given move to the corresponding key/String
     *
     * @param a - a move function used by application
     * @return move as a String represented by "WASD"
     */
    public String convertMoveToString(Move a) {
        switch (a) {
            case DOWN:
                return "s";
            case RIGHT:
                return "d";
            case UP:
                return "w";
            case LEFT:
                return "a";
        };
        return null;
    }

    /**
     * writes the Json array builder to a Json file
     * first checks all existing files to makes sure it is not overwriting old files
     * called when saving a game, or when choosing to watch a replay.
     */
    public void writeJsonToFile() {
        if(file.exists()) {
            createNewSaveIteration();
        }

        try {
            FileWriter fw = new FileWriter(new File(dir + fileName)); //create a file in the correct directory
            JsonWriter jw = Json.createWriter(fw); //convert to json file
            jw.writeArray(arrayBuilder.build()); //builds json array and writes to file
            jw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates the file name to a new iterations i.e. changes the saveIteration
     * finds lowest possible number. i.e if a file is deleted the new save will take its place
     */
    private void createNewSaveIteration() {
        File recordDir = new File(dir);

        File[] currentRecords = recordDir.listFiles(); //gets all files from the directory
        if(currentRecords == null){ return; } //if no files exists no need to increase save number

        for(File records : currentRecords) {
            if(records.getName().contains(fileNameSuffix)){ //checks only files with .json suffixes
                String stringIterator = records.getName().
                        substring(records.getName().
                                indexOf(fileNamePrefix) + 7, records.getName().indexOf(".")); //limits reader to only the integer in the file name

                int fileInt = Integer.parseInt(stringIterator) + 1; //add one to the highest/last file
                if(fileInt > saveIteration) {
                    saveIteration = fileInt; //check to make sure highest possible file int will be used
                }
            }
        }
        fileName = fileNamePrefix + saveIteration + fileNameSuffix;
    }
}
