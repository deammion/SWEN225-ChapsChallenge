
package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import nz.ac.vuw.ecs.swen225.gp30.Move;


import java.util.ArrayList;

public class Replay {

    public Boolean autoPlaying = false;
    private int playerIndex = 0;
    private int actorIndex = 0;
    private double delay = 1;

    public ArrayList<String> playerMoves = new ArrayList<>();
    public ArrayList<String> actorMoves = new ArrayList<>();

    /**
     * Called by the application class, which allows user to select Json file to load,
     * feeds file to LoadJson class which will update the local gameStates arraylist
     *
     * @param fileName - user selected filename, see LoadJSON
     */
    public void loadJsonToReplay(String fileName) {
        LoadJSON lj = new LoadJSON();
        playerMoves = lj.loadPlayerMoves(fileName);
        actorMoves = lj.loadActorMoves(fileName);
    }

    /**
     * toggles the auto play function, and resets the play back timer
     */
    public void setAutoPlaying() {
        autoPlaying = !autoPlaying;
    }

    /**
     * gets the time from the next move in the playerMoves arraylist
     * checks that against the game time, if the move time is less then the game timer
     * the move is passed to application.
     *
     * @Param time - game timer
     * @return Move - to be passed to application
     */
    public Move autoPlay(int time) {
        while (autoPlaying) {
            int playerMoveTime = convertStringToInt(playerMoves.get(playerIndex));
            if (playerMoveTime < time) {
                char stringMove = playerMoves.get(playerIndex).charAt(0);
                Move move = convertStringTOMove(stringMove);
                playerIndex++;
                return move;
            }
        }
        return null;
    }

    /**
     * gets the time from the next move in the actorMoves arraylist
     * checks that against the game time, if the move time is less then the game timer
     * the move is passed to application.
     *
     * @Param time - game timer
     * @return Move - to be passed to application
     */
    public Move autoPlayActor(int time) {
        if(!actorMoves.isEmpty()) {
            int actorMoveTime = convertStringToInt(actorMoves.get(actorIndex));
            if(actorMoveTime < time){
                char stringMove = actorMoves.get(actorIndex).charAt(0);
                Move actorMove = convertStringTOMove(stringMove);
                actorIndex++;
                return actorMove;
            }
        }
        return  null;
    }

    /**
     * gets the time from the actor arraylist and player arraylist
     * compares these two ints, and returns the move with the lowest time.
     * needs to be seperate from the auto play function to account for timer
     *
     * @return Move - to be passed to application
     */
    public Move playNextMove() {
        if(playerIndex + 1 <= playerMoves.size() && !autoPlaying) {
            int playerTime = convertStringToInt(playerMoves.get(playerIndex + 1));
            int actorTime = convertStringToInt(actorMoves.get(actorIndex + 1));
            if (actorTime < playerTime) {
                actorIndex++;
                return convertStringTOMove(playerMoves.get(actorIndex).charAt(0));
            }
            playerIndex++;
            return convertStringTOMove(playerMoves.get(playerIndex).charAt(0));
        }
        return null;
    }

    /**
     * gets the previous game state in the gameStates arraylist
     * so it can be passed to the maze via levels to render
     * called by Application
     *
     * @return String - String that can be read into a gameState
     */
    public void getPreviousMove() {
        if(playerIndex - 1 >= 0 && !autoPlaying) {
            playerIndex--;
            Move playerMove = reverseMove(playerMoves.get(playerIndex).charAt(0));
            int playerTime = convertStringToInt(playerMoves.get(playerIndex));
            int actorTime = convertStringToInt(actorMoves.get(actorIndex));
            if (actorTime > playerTime) {
                //pass actormove to app
                Move actorMove = reverseMove(actorMoves.get(actorIndex).charAt(0));
                actorIndex--;
            }
            //pass player move to app
            playerIndex++;
        }
    }

    /**
     * called by application, increases the time between steps on autoplay function
     */
    public void decreaseDelay(){
        if(delay < 4) {
            delay = delay*2;
        } else {
            delay = 4;
        }
    }

    /**
     * called by Application, decreases the time between steps on autoplay function
     */
    public void increaseDelay() {
        if (delay > .5) {
            delay = delay/2;
        } else {
            delay = 0.5;
        }
    }

    public Move convertStringTOMove(Character s) {
        return switch (s) {
            case 's' -> Move.DOWN;
            case 'd' -> Move.RIGHT;
            case 'a' -> Move.LEFT;
            case 'w' -> Move.UP;
            default -> null;
        };
        // throw error
    }

    public Move reverseMove(Character s){
        return switch (s) {
            case 'w' -> Move.DOWN;
            case 'a' -> Move.RIGHT;
            case 'd' -> Move.LEFT;
            case 's' -> Move.UP;
            default -> null;
        };
        // throw error
    }

    public int convertStringToInt(String s){
        return Integer.parseInt(s,1,s.length(),10);
    }

}
