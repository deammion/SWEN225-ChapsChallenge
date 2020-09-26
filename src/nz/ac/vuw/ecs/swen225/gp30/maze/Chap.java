package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;

import java.util.HashSet;
import java.util.Set;

public class Chap {
    private int x, y;
    private final Set<Item> inventory;
    private int chipsCollected;

    public Chap() {
        inventory = new HashSet<>();
        chipsCollected = 0;
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

    public int getChipsCollected() {
        return chipsCollected;
    }

    public void collectChip() {
        chipsCollected++;
    }

    public Set<Item> getInventory() {
        return inventory;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }

    public boolean consumeItem(Item item) {
        return inventory.remove(item);
    }
}
