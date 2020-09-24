
package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

public class Replay extends JFrame {

    private Boolean autoPlaying = false;
    private JPanel panel;
    private Timer timer;
    private int index = 0;

    public ReplayGUI(String maze, ArrayList<String> gameStates) {

    }

    public String getNextGameState(ArrayList<String> gameStates) {
        String nextState = gameStates.get(index+1);
        if(nextState == null){
            return null;
        } else {
            return nextState;
        }
    }

    public String getPreviousGameState(ArrayList<String> gameStates) {
        String prevState = gameStates.get(index-1);
        if(prevState == null) {
            return null;
        } else {
            return prevState;
        }
    }
}
