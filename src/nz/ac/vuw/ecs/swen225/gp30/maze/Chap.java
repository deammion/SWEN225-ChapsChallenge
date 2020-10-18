package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Chap extends GameObject {
    private int x, y;
    private final List<Item> inventory;
    private int chipsCollected;
    private boolean active;
    private Move dir;

    public Chap(int x, int y) {
        this.x = x;
        this.y = y;
        inventory = new ArrayList<>();
        chipsCollected = 0;
        active = true;
        dir = Move.DOWN;
    }

    public void setAt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDirection(Move dir) {
        this.dir = dir;
    }

    @Override
    public String getImageString() {
        switch(dir) {
            case UP:
                return "actor_chap_up.png";
            case DOWN:
                return "actor_chap_down.png";
            case LEFT:
                return "actor_chap_left.png";
            case RIGHT:
                return "actor_chap_right.png";
        }
        return null;
    }

    public int getChipsCollected() {
        return chipsCollected;
    }

    public void collectChip() {
        chipsCollected++;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }

    public boolean useItem(Item item) {
        return item.isConsumable()? inventory.remove(item) : hasItem(item);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Chap: {"
            + "\n\tx: " + x
            + "\n\ty: " + y
            + "\n\tchips_collected: " + chipsCollected
            + "\n\tinventory: " + inventory.toString()
            + "\n}";
    }
}
