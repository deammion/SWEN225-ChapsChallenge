package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import nz.ac.vuw.ecs.swen225.gp30.Move;

import javax.swing.*;
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
    private ArrayList<String> playerMoves = new ArrayList<>();
    private Boolean autoPlaying = false;
    private int playerIndex = 0;
    private int delay = 1;

    public int level; //made public as getter method wasn't working correctly


    /**
     * Called by the application module, which allows user to select Json file to load,
     * feeds file name to LoadJson class which will update the local playerMoves arraylist and level int
     * playerMoves are used by other methods in this class.
     * level is passed to application to ensure correct level is loaded
     */
    public void loadJsonToReplay() {
        LoadJSON lj = new LoadJSON();
        JFileChooser fileChooser = new JFileChooser(new File("src/nz/ac/vuw/ecs/swen225/gp30/recnplay"));
        int fileReturnValue = fileChooser.showOpenDialog(null);
        if(fileReturnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileSuffix = selectedFile.getName().substring(selectedFile.getName().indexOf("."));
            if (!fileSuffix.equals(".json")) { //checks for correct file format
                System.out.println("Incompatible File Type");
                loadJsonToReplay(); // called recursively to prevent incompatible file being selected
            } else {
                playerMoves = lj.loadPlayerMoves(selectedFile.getName());
                level = lj.loadLevel(selectedFile.getName());
                System.out.println(selectedFile.getName());
            }
        } else { //if no file is selected or file selector is closed method is called again to prevent system progressing
            System.out.println("Please select a file");
            loadJsonToReplay();
        }
    }

    /**
     * gets the time from the next move in the playerMoves arraylist
     * checks that against the game time, if the move time is greater then the game timer (as the timer counts down)
     * the move is passed to application.
     *
     * @param tick - game timer based on ticks
     * @return Move - to be passed to application
     */
    public Move autoPlay(int tick) {
        if (autoPlaying) {
            if(playerIndex < playerMoves.size()){
                int playerMoveTime = convertStringToInt(playerMoves.get(playerIndex));
                int playerDelay = playerMoveTime * delay; //can increase the tick when the move occurred to match the increase in chaps challenge
                if (playerDelay == tick) {
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
        if(playerIndex < playerMoves.size() && !autoPlaying) { //checks there is an available move
            return convertStringToMove(playerMoves.get(playerIndex).charAt(1));
        }
        return null;
    }

    /**
     * increments the playerIndex
     */
    public void incrementPlayIndex() {
        playerIndex++;
    }

    /**
     * returns the tick of the current move used to advance the game clock and actors during step thru replay
     *
     * @return tick - int use to determine how far thur the cycle the the move was
     */
    public int playerMoveTick() {
        return convertStringToInt(playerMoves.get(playerIndex));
    }

    /**
     * resets the autoPlaying Boolean and playerIndex
     * called when a replay has finished
     */
    public void resetAutoPlay() {
        autoPlaying = false;
        playerIndex = 0;
    }

    /**
     * toggles the autoplay boolean
     */
    public void toggleAutoPlaying() {
        autoPlaying = !autoPlaying;
    }

    /**
     * checks if the playerIndex is equal to playerMoves.size()
     * if true the replay has finished
     *
     * @return Boolean - true if all moves in replay have been played
     */
    public boolean endOfReplay(){
        return (playerIndex == playerMoves.size() - 1);
    }

    /**
     * increases the delay to match the delay in the chaps challenge class, increases the tick (timing of the move)
     */
    public void increaseDelay() {
        if(delay < 4) {
            delay = delay * 2;
        }
    }

    /**
     * decreases the delay to match the delay in the chaps challenge class, decreasing the tick (timing of the move)
     */
    public void decreaseDelay() {
        if(delay > 1) {
            delay = delay/2;
        }
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
