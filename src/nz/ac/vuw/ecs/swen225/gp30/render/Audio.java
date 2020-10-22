package nz.ac.vuw.ecs.swen225.gp30.render;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

//template from codejava.net
public class Audio implements LineListener{

	private boolean done;
	private Map<String, File> soundMap;
	private GameWorld game;

	
	public Audio() {
			loadSounds();
	}

		public void setGame(GameWorld game) {
				this.game = game;
		}

		public void loadSounds() {
				soundMap = new HashMap<>();
				File fp = new File("sounds/");
				File[] files = fp.listFiles();
				for(File f : files) {
						soundMap.put(f.getName(), f);
				}
		}

	public void playSound() {
		//AudioInputStream a = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
			try {
					Tile t = game.getMaze().getTileAt(game.getChap().getX(), game.getChap().getY());
					AudioInputStream st = AudioSystem.getAudioInputStream(soundMap.get(t.getSoundString()));
					AudioFormat f = st.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class, f);
					Clip c = (Clip) AudioSystem.getLine(info);
					c.addLineListener(this);
					c.open(st);
					c.start();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.getMessage();
			}
	}

	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.START) {
            System.out.println("Started");
        } else if (type == LineEvent.Type.STOP) {
            done = true;
            System.out.println("Stopped");
        }
	}
}
