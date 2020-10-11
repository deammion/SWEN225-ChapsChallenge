package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import static com.google.common.base.Preconditions.checkArgument;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;

public class WallTile extends Tile {

    public WallTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return false;
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkArgument(chap != null, "chap cannot be null");
        throw new IllegalMoveException("cannot move onto a wall tile");
    }

    @Override
    public void removeChap() {
        throw new RuntimeException("solid tile");
    }

    @Override
    public boolean hasChap() {
        return false;
    }

    @Override
    public char getChar() {
        return '#';
    }

    @Override
    public String getImageString() {
        return "tile_wall.png";
    }
}
