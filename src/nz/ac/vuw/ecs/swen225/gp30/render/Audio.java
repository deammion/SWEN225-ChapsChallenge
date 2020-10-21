package nz.ac.vuw.ecs.swen225.gp30.render;

import java.io.*;

import javax.sound.sampled.*;

//template from codejava.net
public class Audio implements LineListener{

	boolean done;
	
	public Audio() {
	}
	
	public void playSound(String p) throws Exception{
		//AudioInputStream a = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
		File audioFile = new File(p);
        AudioInputStream st = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat f = st.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, f);
        Clip c = (Clip) AudioSystem.getLine(info);
        c.addLineListener(this);
        c.open(st);
        c.start();
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
