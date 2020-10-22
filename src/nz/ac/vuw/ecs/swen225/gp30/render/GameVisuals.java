package nz.ac.vuw.ecs.swen225.gp30.render;

import javax.imageio.ImageIO;
import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameObject;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Mob;
import nz.ac.vuw.ecs.swen225.gp30.maze.MobManager;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * Class to draw tiles, character, items and mobs
 * Can draw the text from info tiles aswell
 * @author gohilpran
 *
 */
public class GameVisuals extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private GameWorld game;
	private final int TILE_SIZE = 42;
	private final int CAMERA_VIEW = 9;
	private final Color BG_COLOR = new Color(144, 164, 174);
	private String infoText;
	public boolean toggleInfo = false;
	public Map<String, BufferedImage> imageMap;
	
	/**
	 * Constructor, setting preffered sizes for tiles
	 */
	public GameVisuals() {
		this.setPreferredSize((new Dimension(TILE_SIZE*CAMERA_VIEW, TILE_SIZE*CAMERA_VIEW)));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		loadImages();
	}

	/**
	 * Loads images into a map
	 */
	public void loadImages() {
		imageMap = new HashMap<>();
		try {
			File fp = new File("assets/");
			File[] files = fp.listFiles();
			for(File f : files) {
				imageMap.put(f.getName(), ImageIO.read(f));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes game field
	 * @param game the gameworld
	 */
	public void setGame(GameWorld game) {
		this.game = game;
	}

	/**
	 * Changes info field
	 * @param text the info text
	 */
	public void setInfoText(String text) {
		this.infoText = text;
	}

	/**
	 * Changes info boolean
	 * @param toggle the boolean to check if info is needed
	 */
	public void toggleInfo(boolean toggle) {
		toggleInfo = toggle;
	}

	/**
	 * Gets tiles to render around chap
	 * @return collection of tiles
	 */
	public Collection<Tile> getTilesToRender() {
		GameObject c = game.getChap();
		int cX = c.getX();
		int cY = c.getY();
		int lowerX = cX - CAMERA_VIEW/2;
		int upperX = cX + CAMERA_VIEW/2;
		int lowerY = cY - CAMERA_VIEW/2;
		int upperY = cY + CAMERA_VIEW/2;

		Predicate<Tile> inRange = tile ->
				tile.getX() >= lowerX && tile.getX() <= upperX && tile.getY() >= lowerY && tile.getY() <= upperY;
		Stream<Tile> allTiles = game.getMaze().getTiles();

		return allTiles.filter(inRange).collect(Collectors.toList());
	}
	
	/**
	 * Gets the x value on the GUI
	 * @param x 2d array x value
	 * @return x co-ord
	 */
	public int getScreenX(int x) {
		return x*TILE_SIZE;
	}
	
	/**
	 * Gets the y value on the GUI
	 * @param y 2d array y value
	 * @return x co-ord
	 */
	public int getScreenY(int y) {
		return y*TILE_SIZE;
	}
	
	/**
	 * Gets the amount to translate in x
	 * @return chap's x minus half camera view
	 */
	public int getTranslateX() {
		int cX = game.getChap().getX();
		return getScreenX(cX - CAMERA_VIEW/2);
	}
	
	/**
	 * Gets the amount to translate in y
	 * @return chap's y minus half camera view
	 */
	public int getTranslateY() {
		int cY = game.getChap().getY();
		return getScreenY(cY - CAMERA_VIEW/2);
	}
	
	/**
	 * Draws the tiles
	 * @param g
	 */
	public void renderTiles(Graphics g) {
		for(Tile t : getTilesToRender()) {
			int screenX = getScreenX(t.getX());
			int screenY = getScreenY(t.getY());
			g.drawImage(imageMap.get(t.getImageString()), screenX, screenY, null);
		}
	}
	
	/**
	 * Draws chap
	 * @param g
	 */
	public void renderChap(Graphics g) {
		GameObject chap = game.getChap();
		int screenX = getScreenX(chap.getX());
		int screenY = getScreenY(chap.getY());
		Tile t = game.getMaze().getTileAt(chap.getX(), chap.getY());
		g.drawImage(imageMap.get(chap.getImageString()), screenX, screenY, null);
	}
	
	/**
	 * Draws enemies
	 * @param g
	 */
	public void renderMobs(Graphics g) {
		MobManager m = game.getMobManager();
		for(Mob mo: m.getMobs()) {
			int screenX = getScreenX(mo.getX());
			int screenY = getScreenY(mo.getY());
			g.drawImage(imageMap.get(mo.getImageString()), screenX, screenY, null);
		}
	}
	
	/**
	 * Calls the draw methods
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(BG_COLOR);
		g.fillRect(0,0, TILE_SIZE*CAMERA_VIEW, TILE_SIZE*CAMERA_VIEW);
		int transX = getTranslateX();
		int transY = getTranslateY();
		g.translate(-transX, -transY);
		renderTiles(g);
		renderChap(g);
		renderMobs(g);
		if(toggleInfo) {
			g.setColor(Color.BLACK);
			int half = (g.getFontMetrics().stringWidth(infoText))/2;
			g.drawString(infoText, getScreenX(game.getChap().getX())-half, getScreenY(game.getChap().getY()));
		}
		g.translate(transX, transY);
		
	}
}
 
 