package nz.ac.vuw.ecs.swen225.gp30.render;

import javax.imageio.ImageIO;
import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameObject;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameVisuals extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	private BufferedImage tile_exit;
	private BufferedImage tile_exit_lock;
	private BufferedImage tile_free;
	private BufferedImage tile_info;
	private BufferedImage tile_treasure;
	private BufferedImage tile_wall;
	private BufferedImage key_blue;
	private BufferedImage key_green;
	private BufferedImage key_red;
	private BufferedImage key_yellow;
	private BufferedImage locked_blue;
	private BufferedImage locked_green;
	private BufferedImage locked_red;
	private BufferedImage locked_yellow;
	*/
	
	private GameWorld game;
	Stream<Tile> allTiles;
	private final int TILE_SIZE = 42;
	private final int CAMERA_VIEW = 9;
	
	public GameVisuals(GameWorld game) {
		this.game = game;
		this.setSize(new Dimension(TILE_SIZE*CAMERA_VIEW, TILE_SIZE*CAMERA_VIEW));
		JFrame j = new JFrame();
		j.setLayout(new FlowLayout());
		j.add(this);
		j.pack();
	}
	
	public List<Tile> getTilesToRender() {
		return game.getMaze().getTiles().collect(Collectors.toList());
	}
	
	public BufferedImage getImageFromObject(GameObject g) {
		try {
			String path = "assets/" + g.getImageString();
			File imgFile = new File(path);
			return ImageIO.read(imgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	private void setImages() {}
		try {
			tile_exit = ImageIO.read(new File("assets/tile_exit.png"));
			tile_exit_lock = ImageIO.read(new File("assets/tile_exit_lock.png"));
			tile_free = ImageIO.read(new File("assets/tile_free.png"));
			tile_info = ImageIO.read(new File("assets/tile_info.png"));
			tile_treasure = ImageIO.read(new File("assets/tile_treasure.png"));
			tile_wall = ImageIO.read(new File("assets/tile_wall.png"));
			key_blue = ImageIO.read(new File("assets/key_blue.png"));
			key_green = ImageIO.read(new File("assets/key_green.png"));
			key_red = ImageIO.read(new File("assets/key_red.png"));
			key_yellow = ImageIO.read(new File("assets/key_yellow.png"));
			locked_blue = ImageIO.read(new File("assets/tile_locked_door_key_blue.png"));
			locked_green = ImageIO.read(new File("assets/tile_locked_door_key_green.png"));
			locked_red = ImageIO.read(new File("assets/tile_locked_door_key_red.png"));
			locked_yellow = ImageIO.read(new File("assets/tile_locked_door_key_yellow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage getImg(char tile) {}
		// list<tiles> 
		// for(t : tiles) { t.imageString(); }
		switch(tile) {
			case 'O':
				return tile_exit;
			case 'X':
				return tile_exit_lock;
			case '_':
				return tile_free;
			case 'i':
				return tile_info;
			case '*':
				return tile_treasure;
			case '#':
				return tile_wall;
		}
		return null;
	}
	*/
	
	public int getScreenX(GameObject g) {
		return g.getX()*TILE_SIZE;
		
	}
	
	public int getScreenY(GameObject g) {
		return g.getY()*TILE_SIZE;
	}
	
	public int getTranslateX() {
		int cX = game.getChap().getX();
		return cX - CAMERA_VIEW/2;
	}
	
	public int getTranslateY() {
		int cY = game.getChap().getY();
		return cY - CAMERA_VIEW/2;
	}
	
	public void renderTiles(Graphics g) {
		int transX = getTranslateX();
		int transY = getTranslateY();
		g.translate(-transX, -transY);
		
		for(Tile t : getTilesToRender()) {
			int screenX = getScreenX(t);
			int screenY = getScreenY(t);
			g.drawImage(getImageFromObject(t), screenX, screenY, null);
		}
		
		g.translate(transX, transY);
	}
	
	public void renderChap(Graphics g) {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		renderTiles(g);
		renderChap(g);
	}
}
 