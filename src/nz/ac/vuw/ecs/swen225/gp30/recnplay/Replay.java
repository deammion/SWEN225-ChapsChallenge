package nz.ac.vuw.ecs.swen225.gp30.recnplay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

public class Replay {

    private Deque<> replayReplayActions = new ArrayDeque();
    private List<> replayPerformedActions = new ArrayList();
    int stepBack = 0;

    public Action stepBack(){
        stepBack++;
        return replayReplayActions.pop();
    }

    public Action stepForward(){
        return replayPerformedActions.get(replayPerformedActions.size()-stepBack);
    }

    public Action autoPlay(int speed){

    }

}
