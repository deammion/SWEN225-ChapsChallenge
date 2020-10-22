package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import nz.ac.vuw.ecs.swen225.gp30.Move;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

/**
 * Replay class stores an arraylist of player moves loaded from JSON files (handled by the LoadJSON class)
 * methods within this class handle iterating this arraylist and passing moves and time to the application class
 *
 * @author tamasedami
 */
public class Replay {

    //all variables initialised when new replay instance is called
    private Boolean autoPlaying = false;
    private int playerIndex = 0;

    public ArrayList<String> playerMoves = new ArrayList<>();
    public int level;

    /**
     * Called by the application module, which allows user to select Json file to load,
     * feeds file to LoadJson class which will update the local playerMoves arraylist and level int
     * playerMoves are used but other methods in this class
     *
     * @return int to load correct level
     */
    public void loadJsonToReplay() {
        LoadJSON lj = new LoadJSON();
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int fileReturnValue = fileChooser.showOpenDialog(null);
        if(fileReturnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileSuffix = selectedFile.getName().substring(selectedFile.getName().indexOf("."));
            if(selectedFile == null){
                System.out.println("No file selected");
            } else if (!fileSuffix.equals(".json")){
                System.out.println("Incompatible File Type");
            }
            playerMoves = lj.loadPlayerMoves(selectedFile.getName());
            level = lj.loadLevel(selectedFile.getName());
            System.out.println(selectedFile.getName());
        }
    }

    /**
     * toggles the autoplay boolean
     */
    public void toggleAutoPlaying() {
        autoPlaying = !autoPlaying;
    }

    /**
     * gets the time from the next move in the playerMoves arraylist
     * checks that against the game time, if the move time is greater then the game timer (as the timer counts down)
     * the move is passed to application.
     *
     * @param time - game timer
     * @return Move - to be passed to application
     */
    public Move autoPlay(int time) {
        if (autoPlaying) {
            if(playerIndex < playerMoves.size()){
                int playerMoveTime = convertStringToInt(playerMoves.get(playerIndex));
                if (playerMoveTime == time) {
                    char stringMove = playerMoves.get(playerIndex).charAt(1);
                    Move move = convertStringToMove(stringMove);
                    playerIndex++;
                    return move;
                }
            }
        }
        return null;
    }

    /**
     * returns the next move in the playerMove arraylist and updates the playerIndex
     *
     * @return Move - to be passed to application
     */
    public Move playNextMove() {
        if(playerIndex + 1 <= playerMoves.size() && !autoPlaying) { //checks there is an available move
            playerIndex++;
            return convertStringToMove(playerMoves.get(playerIndex).charAt(1));
        }
        return null;
    }

    /**
     * gets the time from the current move to return to application to
     * update the game timer
     *
     * @return time - an int representing the game timer
     */
    public int updateTimer() {
        return convertStringToInt(playerMoves.get(playerIndex));
    }

    /**
     * converts a string to a move, the json file stores the moves as strings
     *
     * @param s - string representing movement via "WASD" inputs
     * @return move - to be called by application
     */
    public Move convertStringToMove(Character s) {
        switch (s) {
            case 's':
                return Move.DOWN;
            case 'd':
                return Move.RIGHT;
            case 'a':
                return Move.LEFT;
            case 'w':
                return Move.UP;
        }
        return null;
    }

    /**
     * converts a string to an int, use to convert JSON string to int for time
     *
     * @param s - string form of the JSON object. format: ""a99""
     * @return int - time as an int function
     */
    public int convertStringToInt(String s){
        String numbers = s.substring(2, s.length()-1); //create new string which only contains ints
        return Integer.parseInt(numbers);
    }
}
