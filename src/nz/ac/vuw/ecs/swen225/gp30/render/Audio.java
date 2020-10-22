package nz.ac.vuw.ecs.swen225.gp30.render;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.*;

/**
 * Class to play sound
 * @author Pranav Gohil
 *
 */
public class Audio{

	private Map<String, File> soundMap;
	private GameWorld game;

	/**
	 * Consutructor
	 */
	public Audio() {
		loadSounds();
	}

	/**
	 * Sets game field
	 * @param game
	 */
	public void setGame(GameWorld game) {
		this.game = game;
	}

	/**
	 * Load sound files to map
	 */
	public void loadSounds() {
		soundMap = new HashMap<>();
		File fp = new File("sounds/");
		File[] files = fp.listFiles();
		for(File f : files) {
			soundMap.put(f.getName(), f);
		}
	}

	/**
	 * Play sound when moving on current tile
	 */
	public void playSound() {
		try {
			Tile t = game.getMaze().getTileAt(game.getChap().getX(), game.getChap().getY());
			AudioInputStream st = AudioSystem.getAudioInputStream(soundMap.get(t.getSoundString()));
			AudioFormat f = st.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, f);
			Clip c = (Clip) AudioSystem.getLine(info);
			c.open(st);
			c.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.getMessage();
		}
	}
}
