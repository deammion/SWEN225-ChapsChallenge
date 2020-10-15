
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
    public void loadJsonToreplay(String fileName) {
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
     * calls the getNextGameState method after a delay whilst auto play function/boolean is true
     */
    public void autoPlay(int time) {
        while(autoPlaying) {
            int playerMoveTime = convertStringToInt(playerMoves.get(playerIndex));
                if(playerMoveTime < time){
                    char move = playerMoves.get(playerIndex).charAt(0);
                    convertStringTOMove(move);
                    //send to application to move chap and actor
                    playerIndex++;
                    }
                
            if(!actorMoves.isEmpty()) {
                int actorMoveTime = convertStringToInt(actorMoves.get(actorIndex));
                if(actorMoveTime < time){
                    char actorMove = actorMoves.get(actorIndex).charAt(0);
                    convertStringTOMove(actorMove);
                    //send to application to move chap and actor
                    actorIndex++;
                }
            }
        }
    }

    /**
     * gets the next game state in the gameStates arraylist
     * so it can be passed to the maze via levels to render
     * can be called by application during step by step play back
     * or by autoplay function
     *
     * @return String - String that can be read into a gameState
     */
    public void playNextMove() {
        if(playerIndex + 1 <= playerMoves.size() && !autoPlaying) {
            playerIndex++;
            Move playerMove = convertStringTOMove(playerMoves.get(playerIndex).charAt(0));
            int playerTime = convertStringToInt(playerMoves.get(playerIndex));
            int actorTime = convertStringToInt(actorMoves.get(actorIndex));
            if (actorTime < playerTime) {
                //pass actormove to app
                actorIndex++;
            }
            //pass player move to app

        }
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
        switch (s){
            case 's':
                return Move.DOWN;
            case 'd':
                return Move.RIGHT;
            case 'a':
                return Move.LEFT;
            case 'w':
                return Move.UP;
        }
        return null; // throw error
    }

    public Move reverseMove(Character s){
        switch (s){
            case 'w':
                return Move.DOWN;
            case 'a':
                return Move.RIGHT;
            case 'd':
                return Move.LEFT;
            case 's':
                return Move.UP;
        }
        return null; // throw error
    }

    public int convertStringToInt(String s){
        return Integer.parseInt(s,1,s.length(),10);
    }
}
