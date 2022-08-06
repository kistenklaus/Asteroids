package game.particleSystem;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import engine.math.Vector2D;

public class NonFixedPS {
	
	private List<Particle> particels;
	
	public NonFixedPS() {
		this.particels = new ArrayList<>();
	}
	
	
	public void update(double fD) {
		for(int i = 0; i < particels.size(); i++) {
			if(particels.get(i).update(fD)) {
				particels.remove(i);
				i--;
			}
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
	
	public void explosion(Vector2D pos, int resolution, float size) {
		for(int i = 0; i < resolution; i++) {
			particels.add(new BoomParticle(new Vector2D(pos), 1f, size));
		}
	}
}
