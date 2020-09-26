package nz.ac.vuw.ecs.swen225.gp30.maze;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import static com.google.common.base.Preconditions.checkArgument;

public class Game {
    GameState state;
    Chap gameChap;
    Maze gameMaze;
    // Level level

    public Game() {
        loadLevel();
    }

    public void run() {
        checkArgument(gameChap != null, "chap cannot be null");
        if(state == GameState.PAUSED) {
            return;
        }
    }

    public Tile getNewTile(Move move, int oldX, int oldY) {
        switch(move) {
            case UP: return gameMaze.getTileAt(oldX, oldY+1);
            case DOWN: return gameMaze.getTileAt(oldX, oldY-1);
            case LEFT: return gameMaze.getTileAt(oldX-1, oldY);
            case RIGHT: return gameMaze.getTileAt(oldX+1, oldY);
        }
        return null;
    }

    public boolean moveChap(Move move) {
        int oldX = gameChap.getX();
        int oldY = gameChap.getY();
        Tile oldTile = gameMaze.getTileAt(oldX, oldY);
        Tile newTile = getNewTile(move, oldX, oldY);

        if(!newTile.canMoveTo(gameChap)) { return false; }
        else {
            oldTile.removeChap();
            setChapOnMaze(newTile.getX(), newTile.getY());
        }
        return true;
    }

    public void setChapOnMaze(int x, int y) {
        Preconditions.checkArgument(gameChap != null, "chap cannot be null");
        Tile tile = gameMaze.getTileAt(x, y);
        tile.addChap(gameChap);
    }

    public void resumeGame() {
        state = GameState.RUNNING;
    }

    public void pauseGame() {
        state = GameState.PAUSED;
    }

    public void saveGame() { }

    public void exitGame() { }

    public void setChap(Chap chap) {
        gameChap = chap;
    }

    public Chap getChap() {
        return gameChap;
    }

    public void setMaze(Maze maze) {
        gameMaze = maze;
    }

    public Maze getMaze() {
        return gameMaze;
    }

    public GameState getGameState() {
        return state;
    }

    private void loadLevel() {
    }
}
