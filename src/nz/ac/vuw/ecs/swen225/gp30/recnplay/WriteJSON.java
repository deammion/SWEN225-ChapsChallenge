package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import com.google.common.base.Preconditions;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.Deque;
import java.util.Queue;
import java.util.Map;

public class WriteJSON {

    public void WriteJSON(Deque replayActions, Queue performedActions){ //will return a JSON
        JsonObject recRplJSON = new JsonObject();
        int iterations = 1; //used to compress JSON file, by combining repeated moves


        recRplJSON.put("performedActions",-1);
        While(!performedActions.isEmpty()) {

            new Action action = performedActions.poll();
            if (action == performedActions.peek()) {
                iterations++;
                performedActions.poll();
            } else {
                recRplJSON.put(getActionDescription(Action), iterations);
                iterations = 1; //reset iterations
            }
        }

        recRplJSON.put("replayActions", 0);
        while(!replayActions.isEmpty()) {
            new Action action = replayActions.pop();
            if(action == replayActions.peek()){
                iterations++;
                replayActions.pop();
            } else {
                recRplJSON.put(getActionDescription(Action), iterations);
                iterations = 1;
            }
        }
    }

    public String getActionDescription(Action a){ //cases to be added
        switch(this) {
            case :
                return "Up";
            case :
                return "Down";
            case :
                return "Left";
            case :
                return "Right";
            case :
                return "Pick up Key";
            case :
                return "Drop Key";
            case :
        }
    }
}