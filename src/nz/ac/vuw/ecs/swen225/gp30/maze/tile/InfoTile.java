package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;

import static com.google.common.base.Preconditions.checkArgument;

public class InfoTile extends Tile {

    public InfoTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return true;
    }

    @Override
    public boolean addChap(Chap chap) {
        checkArgument(chap != null, "Chap cannot be null");
        chap.setAt(getX(), getY());
        this.chap = chap;
        return true;
    }

    @Override
    public void removeChap() {
        chap = null;
    }

    @Override
    public boolean hasChap() {
        return chap != null;
    }

    @Override
    public char getChar() {
        return hasChap()? 'c' : 'i';
    }

    @Override
    public String getImageString() {
        return "tile_info.png";
    }
}
