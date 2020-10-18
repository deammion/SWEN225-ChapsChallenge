package nz.ac.vuw.ecs.swen225.gp30.maze.item;

public enum Item {
    KEY_BLUE(ItemType.KEY, true),
    KEY_GREEN(ItemType.KEY, false),
    KEY_RED(ItemType.KEY, true),
    KEY_YELLOW(ItemType.KEY, true);

    private final ItemType type;
    private final boolean consumable;


    Item(ItemType type, boolean consumable) {
        this.type = type;
        this.consumable = consumable;
    }

    public ItemType getType() {
        return type;
    }

    public boolean isConsumable() {
        return consumable;
    }
}
