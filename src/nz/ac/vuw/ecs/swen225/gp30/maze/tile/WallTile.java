package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import static com.google.common.base.Preconditions.checkArgument;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;

public class WallTile extends Tile {

    public WallTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo() {
        return false;
    }

    @Override
    public boolean addChap(Chap chapToAdd) {
        checkArgument(chapToAdd != null, "chap cannot be null");
        throw new RuntimeException("solid tile");
    }

    @Override
    public boolean removeChap() {
        throw new RuntimeException("solid tile");
    }

    @Override
    public boolean hasChap() {
        return false;
    }
}
