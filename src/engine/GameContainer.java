package engine;

import java.awt.Graphics2D;

public interface GameContainer {
	
	public void init(Engine engine);
	
	public void update(double fD);
	
	public void render(Graphics2D g);
	
}
