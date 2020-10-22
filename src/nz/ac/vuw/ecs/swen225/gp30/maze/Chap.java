package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * The Chap class is a type of actor controlled by the player. 
 * It is the main character of the game.
 * The class provides moving functionality, inventory storage, activeness, and direction for animation.
 * 
 * 
 * @author campliosca
 *
 */
public class Chap extends Actor {
    private final List<Item> inventory;
    private int chipsCollected;
    private boolean active;
    
    /**
     * Constructs a Chap object with x and y position.
     * 
     * @param x the x position
     * @param y the y position
     */
    public Chap(int x, int y) {
        super(x, y);
        inventory = new ArrayList<>();
        chipsCollected = 0;
        active = true;
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

    /**
     * Returns the amount of chips collected by chap.
     * 
     * @return the amount of chips collected by chap
     */
    public int getChipsCollected() {
        return chipsCollected;
    }

    /**
     * Increments amount of chips chap has by 1.
     */
    public void collectChip() {
        chipsCollected++;
    }

    /**
     * Returns chaps inventory as a list of items.
     * 
     * @return chaps inventory
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Adds an item to chaps inventory.
     * 
     * @param item the item to be added to inventory
     */
    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    /**
     * Returns true if chap has the item in his inventory.
     * 
     * @param item the item to check
     * @return true if chap has the item in his inventory
     */
    public boolean hasItem(Item item) {
        return inventory.contains(item);
    }

    /**
     * Removes the item from chaps inventory if he has it. Returns true if the item was removed.
     * 
     * @param item the item to remove
     * @return true if the item was consumed
     */
    public boolean useItem(Item item) {
        return item.isConsumable()? inventory.remove(item) : hasItem(item);
    }

    /**
     * Sets the active state of chap.
     * 
     * @param active the active state to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns true if chap is active.
     * 
     * @return true if chap is active
     */
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
            + "\n\timage: " + getImageString()
            + "\n}";
    }
}
