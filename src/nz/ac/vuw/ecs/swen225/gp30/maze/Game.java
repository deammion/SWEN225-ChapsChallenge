package nz.ac.vuw.ecs.swen225.gp30.maze;

public class Game {
    private enum GameState {
        RUNNING,
        PAUSED,
        INFO
    }

    GameState state;
    Chap chap;
    // Level level

    public Game() {
        loadLevel();
    }

    public void move() { }

    public void resumeGame() { }

    public void pauseGame() { }

    public void saveGame() { }

    public void exitGame() { }

    public Chap getChap() {
        return null;
    }

    public GameState getGameState() {
        return state;
    }

    private void loadLevel() {
    }
}
