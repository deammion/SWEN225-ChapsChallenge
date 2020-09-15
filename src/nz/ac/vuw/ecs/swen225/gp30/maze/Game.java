package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import static com.google.common.base.Preconditions.checkArgument;

public class Game {
    private enum GameState {
        RUNNING,
        INFO,
        PAUSED
    }

    GameState state;
    Chap gameChap;
    Maze gameMaze;
    // Level level

    public Game() {
        loadLevel();
    }

    public void run() {
        // checkArgument(level != null);
        checkArgument(gameChap != null, "chap cannot be null");
        if(state == GameState.PAUSED) { return; }


    }

    public Tile getNewTile(Move move, int oldX, int oldY) {
        switch(move) {
            case UP: return gameMaze.getTileAt(oldX, oldY+1);
            case DOWN: return gameMaze.getTileAt(oldX, oldY-1);
            case LEFT: return gameMaze.getTileAt(oldX-1, oldX+1);
            case RIGHT: return gameMaze.getTileAt(oldX+1, oldY+1);
        }
        return null;
    }

    public void move(Move move) {
        int oldX = gameChap.getX();
        int oldY = gameChap.getY();
        Tile oldTile = gameMaze.getTileAt(oldX, oldY);
        Tile newTile = getNewTile(move, oldX, oldY);

        oldTile.removeChap();
        newTile.addChap(gameChap);
        gameChap.setX(newTile.getX());
        gameChap.setY(newTile.getY());
    }

    public void resumeGame() {
        state = GameState.RUNNING;
    }

    public void pauseGame() {
        state = GameState.PAUSED;
    }

    public void saveGame() { }

    public void exitGame() {  }

    public void setChap(Chap chap) { gameChap = chap; }

    public Chap getChap() {
        return gameChap;
    }

    public void setMaze(Maze maze) { gameMaze = maze; }

    public Maze getMaze() {
        return gameMaze;
    }

    public GameState getGameState() {
        return state;
    }

    private void loadLevel() {
    }
}
