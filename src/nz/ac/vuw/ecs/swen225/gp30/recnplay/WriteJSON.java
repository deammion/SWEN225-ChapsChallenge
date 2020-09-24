package nz.ac.vuw.ecs.swen225.gp30.recnplay;


import javax.json.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import nz.ac.vuw.ecs.swen225.gp30.maze.Maze;

public class WriteJSON {


    private final File file;
    private final String dir = "src/nz/ac/vuw/ecs/swen225/gp30/recnplay/";
    private final String fileNamePrefix = "record-";
    private final String fileNameSuffix = ".json";
    private String fileName;
    private int mazeIndex = 0;
    private int moveIndex =0;
    private int saveIteration = 1;
    private final JsonArrayBuilder arrayBuilder;

    public WriteJSON() {
        fileName = fileNamePrefix + saveIteration + fileNamePrefix;
        file = new File(dir + fileNamePrefix + saveIteration + fileNameSuffix);
        arrayBuilder = Json.createArrayBuilder();
    }

    private void createNewSaveIteration() {
        File recordDir = new File(dir);

        File[] currentRecords = recordDir.listFiles();
        if(currentRecords == null){ return; }

        for(File records : currentRecords) {
            if(records.getName().contains(fileNameSuffix)){
                String stringIterator = records.getName().substring(records.getName().indexOf(fileNamePrefix) + 7, records.getName().indexOf("."));

                saveIteration = Integer.parseInt(stringIterator) + 1;

                fileName = fileNamePrefix + saveIteration + fileNameSuffix;
            }
        }
    }

    public void storeGameState (GameObject[][] maze) {
        String s = "";

        for(int rows = 0; rows < maze[0].length; rows++) {
            for(int cols = 0; cols < maze.length; cols++) {
                if(maze[cols][rows] != null){
                    if(cols < maze.length) {
                        s += maze[cols][rows].getmazedescription();//FIXME get write method to get tile description
                    }
                }
            }
            s += "\n";
        }

        JsonObjectBuilder mazeObj = Json.createObjectBuilder().add("maze " + mazeIndex++, s);
        arrayBuilder.add(mazeObj);
    }

    public void storePlayerAction (String playerAction) {
        JsonObjectBuilder action = Json.createObjectBuilder().add("action " + moveIndex++,playerAction);
        arrayBuilder.add(action);
    }

    public void writeJsonToFile() {
        if(file.exists()) {
            createNewSaveIteration();
        }

        try {
            FileWriter fw = new FileWriter(new File(dir + fileName));
            JsonWriter jw = Json.createWriter(fw);
            jw.writeArray(arrayBuilder.build());
            jw.close();
            fw.close();
            //add line to tell user of save file name
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}