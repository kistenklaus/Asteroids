package game.starship;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.math.Vector2D;
import game.MetheorSystem;

public class Pixel extends Projectile{

	protected Pixel(Vector2D pos, Vector2D vel, MetheorSystem ms) {
		super(pos, vel, ms);
	}

	@Override
	protected void draw(Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillRect(-2, -2, 4, 4);
	}
	
	
}
