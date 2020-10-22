package nz.ac.vuw.ecs.swen225.gp30.render;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;

//template from codejava.net
public class Audio implements LineListener{

	private boolean done;
	public Map<String, BufferedImage> soundMap;
	
	public Audio() {
			loadSounds();
	}

		public void loadSounds() {
				soundMap = new HashMap<>();
				try {
						File fp = new File("assets/");
						File[] files = fp.listFiles();
						for(File f : files) {
								soundMap.put(f.getName(), ImageIO.read(f));
						}
				} catch (IOException e) {
						e.printStackTrace();
				}
		}

	public void playSound(String p) {
		//AudioInputStream a = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
			try {
					File audioFile = new File(p);
					AudioInputStream st = AudioSystem.getAudioInputStream(audioFile);
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
            System.out.println("Started.");
        } else if (type == LineEvent.Type.STOP) {
            done = true;
            System.out.println("Stopped.");
        }
	}
}
