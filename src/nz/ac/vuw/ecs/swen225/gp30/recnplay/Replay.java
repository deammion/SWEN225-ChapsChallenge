
package nz.ac.vuw.ecs.swen225.gp30.recnplay;


import java.util.ArrayList;

public class Replay {

    private Boolean autoPlaying = false;
    private int index = 0;
    private int delay = 5000;

    public ArrayList<String> gameStates = new ArrayList<>();

    /**
     * Called by the application class, which allows user to select Json file to load,
     * feeds file to LoadJson class which will update the local gameStates arraylist
     *
     * @param fileName - user selected filename, see LoadJSON
     */
    public void loadJsonToreplay(String fileName) {
        gameStates = LoadJSON.loadGameStates(fileName);
    }

    /**
     * toggles the auto play function, and resets the play back timer
     */
    public void setAutoPlaying() {
        autoPlaying = !autoPlaying;
        delay = 5000;
    }

    /**
     * calls the getNextGameState method after a delay whilst auto play function/boolean is true
     */
    public void autoPlay() {
        while(autoPlaying) {
            try {
                Thread.sleep(delay);
                getNextGameState();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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
    public String getNextGameState() {
        if(index + 1 <= gameStates.size() && !autoPlaying) {
            index++;
            return gameStates.get(index);
        } else {
            return null;//FIXME replace with error message maybe
        }
    }

    /**
     * gets the previous game state in the gameStates arraylist
     * so it can be passed to the maze via levels to render
     * called by Application
     *
     * @return String - String that can be read into a gameState
     */
    public String getPreviousGameState() {
        if(index - 1 >= 0 && !autoPlaying) {
            index--;
            return gameStates.get(index);
        } else {
            return null;//FIXME replace with error message maybe
        }
    }

    /**
     * called by application, increases the time between steps on autoplay function
     */
    public void increaseDelay(){
        if(delay < 10000) {
            delay += 1000;
        } else {
            delay = 10000;
        }
    }

    /**
     * called by Application, decreases the time between steps on autoplay function
     */
    public void decreaseDelay() {
        if (delay > 1000) {
            delay -= 1000;
        } else {
            delay = 1000;
        }
    }
}
