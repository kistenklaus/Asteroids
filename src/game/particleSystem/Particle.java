package game.particleSystem;

import java.awt.Graphics2D;

import engine.math.Vector2D;

public abstract class Particle {
	
	protected Vector2D pos;
	protected Vector2D vel;
	protected float timer, deathTime;
	
	protected Particle(Vector2D pos, Vector2D vel, float deathTime) {
		this.pos = pos;
		this.vel = vel;
		this.deathTime = deathTime;
		this.timer = 0;
	}
	
	public boolean update(double fD) {
		
		this.timer+=fD;
		if(timer > deathTime)return true;
		
		this.pos.add(Vector2D.MULT(vel, fD));
		return false;
	}
	
	public void render(Graphics2D g) {
		draw(g);
	}
	
	protected abstract void draw(Graphics2D g);
	
}
