package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;

public class TreasureTile extends Tile {
    Boolean collected = false;

    public TreasureTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return true;
    }

    @Override
    public boolean addChap(Chap chap) {
        if(!collected) {
            chap.collectChip();
            collected = true;
        }
        chap.setAt(getX(), getY());
        this.chap = chap;
        return false;
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
        return hasChap() ? 'c' : collected? '_' : '*';
    }
}
