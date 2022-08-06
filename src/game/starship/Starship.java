package game.starship;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import engine.Input;
import engine.Window;
import engine.math.Vector2D;
import game.MetheorSystem;
import game.particleSystem.ParticleSystem;

public class Starship {
	
	private Polygon model;
	private Vector2D pos, drift;
	private Input input;
	private ParticleSystem ps;
	private MetheorSystem ms;
	private Rectangle bound;
	
	private List<Projectile> projectiles;
	
	private float vel;
	private double rotation;
	private boolean alive;
	
	private float timeSinceLastShot, shotspeed;
	
	public Starship(Input input, MetheorSystem ms) {
		this.input = input;
		this.ms = ms;
		
		this.projectiles = new ArrayList<>();
		
		this.model = new Polygon(
				 new int[] {0, -7, 7}
				,new int[] {10, -10, -10}
				,3
				);
		
		this.pos = new Vector2D(Window.width/2, Window.height/2);
		this.drift = new Vector2D();
		
		this.bound = new Rectangle((int)pos.getX()-2,(int)pos.getY()-2,4,4);
		
		this.ps = new ParticleSystem(pos);
		
		this.rotation = 0d;
		this.vel = 0f;
		this.alive = true;
		
		this.timeSinceLastShot = 0;
		this.shotspeed = 0.25f;
	}
	
	public void update(final double fD) {
		
		if(alive) {
			if(ms.intersects(bound)) {
				alive = false;
				ps.explode(100);
				ps.disableThrust();
				return;
			}
			
			if(input.isKeyDown(Input.A)) {
				rotation -= 4d * fD;
			}
			if(input.isKeyDown(Input.D)) {
				rotation += 4d * fD;
			}
			if(rotation > Math.PI*2)rotation -= Math.PI*2;
			if(rotation < 0)rotation += Math.PI*2;
			
			Vector2D dir = new Vector2D(Math.cos(rotation+Math.PI/2),Math.sin(rotation+Math.PI/2));
			
			if(input.isKeyDown(Input.W)) {
				vel += 10;
				if(vel > 300)vel = 300;
				this.drift.add(new Vector2D(10 * Math.cos(rotation+Math.PI/2) * fD, 10 * Math.sin(rotation+Math.PI/2) * fD));
				ps.enableThrust();
			}else {
				vel *= 0.93;
				ps.disableThrust();
			}
			dir.mult((float)(vel * fD));
			dir.limit(10);
			
			timeSinceLastShot+=fD;
			if(input.isKeyDown(Input.SPACE)	&&  timeSinceLastShot > shotspeed) {
				Vector2D unitShotDir = new Vector2D(
						Math.cos(rotation + Math.PI/2),
						Math.sin(rotation + Math.PI/2)
						);
				this.drift.add(Vector2D.MULT(unitShotDir, -0.2f));
				projectiles.add(new Pixel(new Vector2D(pos), Vector2D.MULT(unitShotDir, 500),
						this.ms));
				timeSinceLastShot = 0;
			}
			
			
			
			//EUERS IDENTIY:
			this.drift.mult(0.96f);
			this.drift.limit(1.5f);
			this.pos.add(dir);
			this.pos.add(drift);
			//<
			this.bound.setLocation((int)pos.getX()-2, (int)pos.getY()-2);
			
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).update(fD)) {
				projectiles.remove(i);
				i--;
			}
		}
		
		ps.update(fD);
	}
	
	public void render(Graphics2D g) {
		ps.render(g);
		
		if(alive) {
			g.setColor(Color.white);
			float x = pos.getX();
			float y = pos.getY();
			double rot = rotation;
			g.translate(x, y);
			g.rotate(rot);
			g.fillPolygon(model);
			g.rotate(-rot);
			g.translate(-x, -y);
		}
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile proj = projectiles.get(i);
			if(proj != null) {
				proj.render(g);
			}
		}
	}
	
	
}
