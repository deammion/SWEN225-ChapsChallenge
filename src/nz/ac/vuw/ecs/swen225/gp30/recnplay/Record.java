
package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import nz.ac.vuw.ecs.swen225.gp30.Move;

import java.util.*;
//FIXME whole class might not be needed if saving only game states, can refactor to split writeJSON class
public class Record {

    private ArrayList<String> playerActions = new ArrayList<>();
    private ArrayList<String> playerActionsReplay = new ArrayList<>();

    private ArrayList<String> actorActions = new ArrayList<>();
    private ArrayList<String> actorActionsReplay = new ArrayList<>();

    public void recordPlayerAction(Move action) {
        String s = convertMoveToString(action);
        recordActions(s);
    }

    public void recordActorActions(Move action) {
        String s = convertMoveToString(action);
        recordActorActions(s);
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

    public void recordActorActions(String s){
        addToActorActions(s);
        addToActorReplay(s);
    }

    public void addToActorActions(String a) { actorActions.add(a); }

    public void addToActorReplay(String a) {
        String revAct = reverseAction(a);
        actorActionsReplay.add(revAct);
    }

    public static String reverseAction(String a) {
        switch (a){
            case "w":
                return "s";
            case "a":
                return "d";
            case "s":
                return "w";
            case "d":
                return "a";
        }
        return null;
    }

    public static String convertMoveToString(Move a) {
        switch (a){
            case DOWN:
                return "s";
            case RIGHT:
                return "d";
            case UP:
                return "w";
            case LEFT:
                return "a";
        }
        return null; // throw error
    }
}