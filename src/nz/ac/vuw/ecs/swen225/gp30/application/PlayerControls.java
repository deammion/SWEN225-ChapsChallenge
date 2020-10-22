package nz.ac.vuw.ecs.swen225.gp30.application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The controls for the player to move Chap around the maze, separated from the other controls
 * class to stop over stepping around the maze.
 *
 * @author jakeh.
 */
public class PlayerControls implements KeyListener {
		private Map<Integer, Key> keys = new HashMap<>();

	/**
	 * The movement keys for Chap.
	 */
	public PlayerControls() {
			bindKey(KeyEvent.VK_UP, Key.up);
			bindKey(KeyEvent.VK_DOWN, Key.down);
			bindKey(KeyEvent.VK_LEFT, Key.left);
			bindKey(KeyEvent.VK_RIGHT, Key.right);
		}

	/**
	 * The key typed.
	 *
	 * @param e - the ke y event.
	 */
		@Override
		public void keyTyped(KeyEvent e) {
		}

	/**
	 * The key pressed.
	 *
	 * @param e - the key event.
	 */
		@Override
		public void keyPressed(KeyEvent e) {
			releaseKeys();
			if(keys.containsKey(e.getKeyCode())) {
				keys.get(e.getKeyCode()).pressed = true;
			}
		}

	/**
	 * The key is released.
	 *
	 * @param e - the key event.
	 */
		@Override
		public void keyReleased(KeyEvent e) {
			if(keys.containsKey(e.getKeyCode())) {
				keys.get(e.getKeyCode()).pressed = false;
			}
		}

	/**
	 * Releases the keys, stop Chap stepping to quickly around the
	 * board.
	 */
	public void releaseKeys() {
			for(Key k : keys.values()) {
				k.pressed = false;
			}
		}

	/**
	 * Binding the key to the action.
	 *
	 * @param code - the key code.
	 * @param key - the key pressed.
	 */
		public void bindKey(Integer code, Key key) {
			keys.put(code, key);
		}
}
