package nz.ac.vuw.ecs.swen225.gp30.recnplay;


import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;


import javax.json.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteJSON {

    //initialises for directory, and standard naming for files
    private final String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
    private final String fileNamePrefix = "record-";
    private final String fileNameSuffix = ".json";

    //initialises ints required for saving gameState and file iterations
    private int saveIteration = 1;
    private int playerMoveIndex = 0;
    private int actorMoveIndex =0;

    //initialises Json array builder, file name and file in order to save gameState in an array and write to file
    private JsonArrayBuilder arrayBuilder;
    private String fileName;
    private File file;

    /**
     * constructor
     */
    public WriteJSON() {
        arrayBuilder = Json.createArrayBuilder();
        fileName = fileNamePrefix + saveIteration + fileNameSuffix;
        file = new File(dir + fileName);
    }

    public void storePlayerMove(Move move,int time) {
        JsonObjectBuilder playerMoveObject = Json.createObjectBuilder().add("Player" + playerMoveIndex++,convertMoveToString(move) + time);
        arrayBuilder.add(playerMoveObject);

    }

    public void storeActorMove(Move move, int time) {
        JsonObjectBuilder actorMoveObject = Json.createObjectBuilder().add("Actor" + actorMoveIndex++,convertMoveToString(move) + time);
        arrayBuilder.add(actorMoveObject);
    }

    /**
     * takes a gameState, iterators through it, saves each "tile" as a string.
     * adds string to a string builder, then creates a Json object using that string.
     * this json object is then added to the Json array builder
     *
     * @param levelNum -String dictating the level number
     */
    public void storeLevel(String levelNum) { //FIXME could just load from level
        JsonObjectBuilder gameStateObj = Json.createObjectBuilder().add("level ", levelNum); //converts level to a JsonObject
        arrayBuilder.add(gameStateObj); //adds JsonObject to JsonArray
    }

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
    public String writeJsonToFile() {
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
        return fileName;
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