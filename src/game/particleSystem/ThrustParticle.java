package game.particleSystem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import engine.math.Vector2D;

public class ThrustParticle extends Particle{
	
	public ThrustParticle(Vector2D pos, float deathTimer) {
		super(pos, new Vector2D(), deathTimer);
		
		Random ran = new Random();
		super.vel = new Vector2D(ran.nextFloat()-0.5f, ran.nextFloat()-0.5f);
		super.vel.mult(25f);
	}

	@Override
	protected void draw(Graphics2D g) {
		float done = timer/deathTime;
		if(done == 0f) done = 0.001f;
		int size = (int)((1-done) * 10);
		g.setColor(new Color(0.6f, 0.6f, 0.6f, (1-done)/1.5f));
		g.fillOval((int)pos.getX()-size/2, (int)pos.getY()-size/2, size, size);
	}
	
}
