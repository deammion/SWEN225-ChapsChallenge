package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import static com.google.common.base.Preconditions.checkArgument;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;

public class FreeTile extends Tile {

    public FreeTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean addChap(Chap chapToAdd) {
        checkArgument(chapToAdd != null, "Chap cannot be null");
        chap = chapToAdd;
        return true;
    }

    @Override
    public boolean removeChap() {
        chap = null;
        return false;
    }

    @Override
    public boolean hasChap() {
        return chap != null;
    }
}
