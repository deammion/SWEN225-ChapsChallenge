package nz.ac.vuw.ecs.swen225.gp30.maze;

import static com.google.common.base.Preconditions.checkArgument;

public class Game {
    private enum GameState {
        RUNNING,
        INFO,
        PAUSED
    }

    GameState state;
    Chap chap;
    // Level level

    public Game() {
        loadLevel();
    }

    public void run() {
        // checkArgument(level != null);
        if(state == GameState.PAUSED) { return; }


    }

    public void move() { }

    public void resumeGame() {
        state = GameState.RUNNING;
    }

    public void pauseGame() {
        state = GameState.PAUSED;
    }

    public void saveGame() { }

    public void exitGame() {  }

    public Chap getChap() {
        return null;
    }

    public GameState getGameState() {
        return state;
    }

    private void loadLevel() {
    }
}
