package nz.ac.vuw.ecs.swen225.gp30.maze;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.ExitTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.InfoTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class GameWorld {
    private Maze maze;
    private Chap chap;
    private String levelInfo;
    public static int CHIPS_REQUIRED;
    public MobManager mobMgr;

    public GameWorld(Maze maze, MobManager mobMgr, Chap chap) {
        this.maze = maze;
        this.chap = chap;
        this.mobMgr = mobMgr;
        setChapOnMaze(chap.getX(), chap.getY());
    }

    public boolean moveChap(Move move) {
        int oldX = chap.getX();
        int oldY = chap.getY();
        Tile oldTile = maze.getTileAt(oldX, oldY);
        Tile newTile = maze.getNewTileFromMove(move, oldX, oldY);

        if(!newTile.canMoveTo(chap)) { return false; }
        else {
            oldTile.removeChap();
            setChapOnMaze(newTile.getX(), newTile.getY());
            chap.setDirection(move);
        }
        return true;
    }

    public void advance() {
        mobMgr.advanceMobs();
    }

    public void setChapOnMaze(int x, int y) {
        Preconditions.checkNotNull(chap);
        Tile tile = maze.getTileAt(x, y);
        tile.addChap(chap);
        assert(tile.getX() == chap.getX() && tile.getY() == chap.getY());
    }

    public boolean isChapOnInfo() {
        Tile t = maze.getTileAt(chap.getX(), chap.getY());
        return t instanceof InfoTile;
    }

    public boolean isChapOnExit() {
        Tile t = maze.getTileAt(chap.getX(), chap.getY());
        return t instanceof ExitTile;
    }

    public boolean isChapActive() {
        return chap.isActive();
    }

    public void setChap(Chap chap) {
        this.chap = chap;
    }

    public Chap getChap() {
        return chap;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMobManager(MobManager m) {
        this.mobMgr = m;
    }

    public MobManager getMobManager() {
        return mobMgr;
    }

    public void setLevelInfo(String info) {
        this.levelInfo = info;
    }

    public String getLevelInfo() {
        return levelInfo;
    }
}
