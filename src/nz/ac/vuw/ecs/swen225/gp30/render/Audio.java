package nz.ac.vuw.ecs.swen225.gp30.render;

import java.io.*;

import javax.sound.sampled.*;

public class Audio {

	private String path;
	
	public Audio(String p) {
		path = p;
	}
	
	public void playSound() throws Exception{
		AudioInputStream a = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
		Clip c = AudioSystem.getClip();
		c.open(a);
		c.start();
	}
}
