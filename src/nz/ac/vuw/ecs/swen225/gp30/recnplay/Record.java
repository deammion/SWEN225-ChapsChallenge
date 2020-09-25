
package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import java.util.*;
//FIXME whole class might not be needed if saving only game states, can refactor to split writeJSON class
public class Record {

    private ArrayList<String> playerActions;
    private ArrayList<String> playerActionsReplay;

    public void recordGameState(String action) {
        playerActions = new ArrayList<>();
        playerActionsReplay = new ArrayList<>();
        recordActions(action);
    }


    public void recordActions(String a){ //will return an action not void
        addToPerformedActions(a);
        addToReplayActions(a);
    }

    public void addToReplayActions(String a){
        String revAct = reverseAction(a);
        playerActionsReplay.add(revAct);
    }

    public void addToPerformedActions(String a){
        playerActions.add(a);
    }

    public static String reverseAction(String a) {
        String revAct = a;
        switch (revAct){
            case "w":
                revAct = "s";
            case "a":
                revAct = "d";
            case "s":
                revAct = "w";
            case "d":
                revAct = "a";
            default:
                break;
        }
        return revAct;
    }
}