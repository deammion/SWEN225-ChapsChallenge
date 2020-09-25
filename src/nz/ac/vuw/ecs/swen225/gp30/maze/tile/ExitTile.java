package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;

public class ExitTile extends Tile {

    public ExitTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return false;
    }

    @Override
    public boolean addChap(Chap chap) {
        return false;
    }

    @Override
    public void removeChap() {
        chap = null;
    }

    @Override
    public boolean hasChap() {
        return false;
    }

    @Override
    public char getChar() {
        return hasChap() ? 'c' : 'O';
    }
}
