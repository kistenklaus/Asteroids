package game.particleSystem;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import engine.math.Vector2D;

public class ParticleSystem {
	
	private Vector2D pos;
	private List<Particle> particels;
	
	private boolean enableThrust;
	
	public ParticleSystem(Vector2D pos) {
		this.pos = pos;
		this.particels = new ArrayList<>();
		this.enableThrust = false;
	}
	
	public void update(double fD) {
		for(int i = 0; i < particels.size(); i++) {
			if(particels.get(i).update(fD)) {
				particels.remove(i);
				i--;
			}
		}
		
		createThrustParticle();
		
	}
	
	private void createThrustParticle() {
		if(enableThrust) {
			particels.add(new ThrustParticle(new Vector2D(pos), 5f));
		}
	}
	
	public void explode(int resolution) {
		for(int i = 0; i < resolution; i++) {
			particels.add(new BoomParticle(new Vector2D(pos), 1f, 75f));
		}
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < particels.size(); i++) {
			Particle part = particels.get(i);
			if(part != null) {
				particels.get(i).render(g);
			}
		}
	}
	
	public void enableThrust() {
		this.enableThrust = true;
	}
	public void disableThrust() {
		this.enableThrust = false;
	}
	public boolean isThrustEnable() {
		return enableThrust;
	}
	
}
