package nz.ac.vuw.ecs.swen225.gp30.maze.item;

public enum Item {
    BLUE_KEY(ItemType.KEY),
    GREEN_KEY(ItemType.KEY),
    YELLOW_KEY(ItemType.KEY);

    public final ItemType type;

    private Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }
}
