package game.particleSystem;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import engine.math.Vector2D;

public class BoomParticle extends Particle{
	
	private float green;
	
	public BoomParticle(Vector2D pos, float deathTime, float expansion) {
		super(pos, new Vector2D(), deathTime);
		Random ran = new Random();
		float theta = (float) (ran.nextFloat()*Math.PI*2);
		super.vel = new Vector2D(Math.cos(theta), Math.sin(theta));
		super.vel.mult(ran.nextFloat()*expansion);
		this.green = 0.1f + ran.nextFloat()*0.5f;
	}

	@Override
	protected void draw(Graphics2D g) {
		float done = timer/deathTime;
		if(done == 0f) done = 0.001f;
		int size = (int)((1-done) * 20);
		g.setColor(new Color(0.8f, green, 0.1f, (1-done)*1f));
		g.fillOval((int)pos.getX()-size/2, (int)pos.getY()-size/2, size, size);
	}
	
	
	
	
}
