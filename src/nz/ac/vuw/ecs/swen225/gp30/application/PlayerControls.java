package nz.ac.vuw.ecs.swen225.gp30.application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class PlayerControls implements KeyListener {
		private Map<Integer, Key> keys = new HashMap<>();

		public PlayerControls() {
				bindKey(KeyEvent.VK_UP, Key.up);
				bindKey(KeyEvent.VK_DOWN, Key.down);
				bindKey(KeyEvent.VK_LEFT, Key.left);
				bindKey(KeyEvent.VK_RIGHT, Key.right);
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
				releaseKeys();
				if(keys.containsKey(e.getKeyCode())) {
						keys.get(e.getKeyCode()).pressed = true;
				}
		}

		@Override
		public void keyReleased(KeyEvent e) {
				if(keys.containsKey(e.getKeyCode())) {
						keys.get(e.getKeyCode()).pressed = false;
				}
		}

		public void releaseKeys() {
				for(Key k : keys.values()) {
						k.pressed = false;
				}
		}

		public void bindKey(Integer code, Key key) {
			keys.put(code, key);
		}
}
