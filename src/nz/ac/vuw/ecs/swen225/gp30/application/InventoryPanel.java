package nz.ac.vuw.ecs.swen225.gp30.application;

import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The inventory panel which will display the information for the
 * player to use while playing the game.
 *
 * @author jakeh.
 */
public class InventoryPanel extends JPanel {
    private final int TILE_SIZE = 42;
    private final int PANEL_TILE_WIDTH = 6;
    private final int PANEL_TILE_HEIGHT = 2;

    List<Item> itemsToDisplay;

    /**
     * The keys in the inventory to be displayed.
     *
     * @param items - the keys
     */
    public void setItemsToDisplay(List<Item> items) {
        this.itemsToDisplay = items;
    }

    /**
     * Inventory panel constructor.
     */
    InventoryPanel() {
        itemsToDisplay = new ArrayList<>();
        setMaximumSize(((new Dimension(TILE_SIZE*PANEL_TILE_WIDTH, TILE_SIZE*PANEL_TILE_HEIGHT))));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

    }

    /**
     * Method to get the correct image to display
     *
     * @param i - the item to get.
     * @return - the image to display.
     */
    public BufferedImage getImage(Item i) {
        String path = "assets/" + (i == null? "tile_free" : "tile_" + i.toString()) + ".png";
        try {
            File imgFile = new File(path);
            return ImageIO.read(imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Painting the component in the panel.
     *
     * @param g - graphics.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int count = 0;
        for(int x = 0; x < PANEL_TILE_WIDTH; x++) {
            for(int y = 0; y < PANEL_TILE_HEIGHT; y++) {
                BufferedImage img;
                img = count >= itemsToDisplay.size() ? getImage(null) : getImage(itemsToDisplay.get(count));
                g.drawImage(img, x*TILE_SIZE, y*TILE_SIZE, null);
                count++;
            }
        }
    }

    /**
     * Repaint the panel.
     */
    @Override
    public void repaint() {
        super.repaint();
    }
}
