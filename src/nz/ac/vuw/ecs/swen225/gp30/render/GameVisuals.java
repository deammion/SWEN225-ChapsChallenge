package nz.ac.vuw.ecs.swen225.gp30.render;

import javax.swing.*;

import nz.ac.vuw.ecs.swen225.gp30.maze.Maze;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameVisuals extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private Maze maze;
	
	public GameVisuals(Maze m) {
		this.maze = m;
		
	}
	
	private void setImages() {
		
	}
	
	@Override
	public void paintComponents(Graphics g) {
		
	}
}
