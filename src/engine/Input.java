package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener{
	
	public static final int A = 0;
	public static final int S = 1;
	public static final int D = 2;
	public static final int W = 3;
	public static final int SPACE = 4;
	public static final int ESCAPE = 5;
	
	boolean[] keys;
	
	public Input() {
		keys = new boolean[6];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) {
			keys[A] = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			keys[S] = true;	
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			keys[D] = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			keys[W] = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[SPACE] = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			keys[ESCAPE] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) {
			keys[A] = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			keys[S] = false;	
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			keys[D] = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			keys[W] = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[SPACE] = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			keys[ESCAPE] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public boolean isKeyDown(int key) {
		return keys[key];
	}

}
