package nz.ac.vuw.ecs.swen225.gp30.maze;

public class Game {
    private enum GameState {
        RUNNING,
        PAUSED,
        INFO
    }

    GameState state;
    // Level level

    public Game() { }

    public void move() { }

    public void resumeGame() { }

    public void pauseGame() { }

    public void saveGame() { }

    public void exitGame() { }

    public GameState getGameState() {
        return state;
    }
}
