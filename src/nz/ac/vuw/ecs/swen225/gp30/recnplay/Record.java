
package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import org.json.simple.JSONObject;

public class Record {

    public Deque<> replayActions = new ArrayDeque();
    public Queue<> performedActions = new ArrayDeque();



    public void recordActions(Action a){ //will return an action not void
        addToPerformedActions(a);
        addToReplayActions(a);
    }

    public void addToReplayActions(Action a){
        new Action revAct = reverseAction(a);
        replayActions.add(revAct);
    }

    public void addToPerformedActions(Action a){
        performedActions.add(a);
    }

    public Action reverseAction(Action a) {
        switch (Action){
            case :
                return ;
        }
    }
}
