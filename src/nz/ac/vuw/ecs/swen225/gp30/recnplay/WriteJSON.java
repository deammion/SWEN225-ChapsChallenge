package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import javax.json.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteJSON {

    //initialises for directory, and standard naming for files
    private final String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
    private final String fileNamePrefix = "record-";
    private final String fileNameSuffix = ".json";

    //initialises ints required for saving gameState and file iterations
    private int gameStateIndex = 0;
    private int moveIndex =0;
    private int saveIteration = 1;

    //initialises Json array builder, file name and file in order to save gameState in an array and write to file
    private JsonArrayBuilder arrayBuilder;
    private String fileName;
    private File file;

    /**
     * reset for Json array builder, fileName and file
     */
    public void newSave() {
        arrayBuilder = Json.createArrayBuilder();
        fileName = fileNamePrefix + saveIteration + fileNamePrefix;
        file = new File(dir + fileName);
    }

//    /**
//     * takes a gameState, iterators through it, saves each "tile" as a string.
//     * adds string to a string builder, then creates a Json object using that string.
//     * this json object is then added to the Json array builder
//     *
//     * @param gameState - 2d array representing the "Maze"/game state
//     */
//    public void storeGameState (Tile[][] gameState) { //FIXME get correct name of gameState object
//        StringBuilder s = new StringBuilder();
//
//        for(int rows = 0; rows < gameState.length; rows++) { //iterate thru gameState Array
//            for(int cols = 0; cols < gameState[rows].length; cols++) {
//                if(gameState[rows][cols] != null) {
//                    s.append(gameState[rows][cols].toString());//FIXME get write method to get tile description
//
//                }
//            }
//            s.append("\n");
//        }
//
//        JsonObjectBuilder gameStateObj = Json.createObjectBuilder().add("gameState " + gameStateIndex++, s.toString()); //converts s to a JsonObject
//        arrayBuilder.add(gameStateObj); //adds JsonObject to JsonArray
//    }

    public void storePlayerAction (String playerAction) { //FIXME not sure if needed
        JsonObjectBuilder action = Json.createObjectBuilder().add("Player " + moveIndex++, playerAction);
        arrayBuilder.add(action);
    }

    public void StoreActorAction (String actorAction) {
        JsonObjectBuilder action = Json.createObjectBuilder().add("Actor " + moveIndex++,actorAction);
        arrayBuilder.add(action);
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
            //FIXME add line to tell user of save file name
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates the file name to a new iterations i.e. changes the saveIteration
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