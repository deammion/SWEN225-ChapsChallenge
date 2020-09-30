package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;

public class ExitLockTile extends Tile {
    int chipsRequired;
    boolean unlocked = false;

    public ExitLockTile(int x, int y, int chipsRequired) {
        super(x, y);
        this.chipsRequired = chipsRequired;
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return unlocked || chap.getChipsCollected() == chipsRequired;
    }

    @Override
    public boolean addChap(Chap chap) {
        if(!canMoveTo(chap)) { throw new RuntimeException("not enough chips"); }
        else { unlocked = true; }
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
        return hasChap() ? 'c' : unlocked? '_' : 'X';
    }

    @Override
    public String getImageString() {
        return unlocked? "free_tile.png" : "tile_exit_lock.png";
    }
}
