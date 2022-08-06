package game.starship;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.Window;
import engine.math.Vector2D;
import game.Metheor;
import game.MetheorSystem;

public abstract class Projectile {
	
	protected Vector2D pos;
	protected Vector2D vel;
	
	protected Rectangle bound;
	
	protected MetheorSystem ms;
	
	protected Projectile(Vector2D pos, Vector2D vel, MetheorSystem ms) {
		this.pos = pos;
		this.vel = vel;
		this.ms = ms;
		this.bound = new Rectangle((int)pos.getX(), (int)pos.getY(), 1, 1);
	}
	
	public boolean update(double fD) {
		if(pos.getX() > Window.width+100 || pos.getX() < -100 ||
				pos.getY() > Window.height+100 || pos.getY() < -100)
			return true;
		
		pos.add(Vector2D.MULT(vel, (float)fD));
		bound.setLocation((int)pos.getX(), (int)pos.getY());
		
		Metheor tar = ms.intersectsWith(bound);
		if(tar != null) {
			tar.damage();
			return true;
		}
		
		
		return false;
	}
	
	public void render(Graphics2D g) {
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		g.translate(x, y);
		draw(g);
		g.translate(-x, -y);
	}
	
	protected abstract void draw(Graphics2D g);
}
