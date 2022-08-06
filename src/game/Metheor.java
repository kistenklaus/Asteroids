package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

import engine.Window;
import engine.math.Vector2D;

public class Metheor {
	
	private Vector2D pos, vel;
	private Polygon model;
//	private Rectangle core;
	private MetheorSystem ms;
	private int mass;
	private float spawnProtection, collitionProtection, timeAlive;
	
	private boolean alive, inside;
	private boolean terminated;
	
	public Metheor(Vector2D pos, Vector2D vel, int mass, MetheorSystem ms) {
		this.pos = pos;
		this.vel = vel;
		this.mass = mass;
		this.ms = ms;
		
		this.terminated = false;
		
		this.spawnProtection = 0.2f;
		this.collitionProtection = 2f;
		
		generateModel(10);
		
//		this.core = new Rectangle(-mass/4, -mass/4, -mass/2, mass/2);
		
		this.alive = true;
		this.inside = false;
	}
	
	private void generateModel(int verticies) {
		int[] xpoints = new int[verticies];
		int[] ypoints = new int[verticies];
		
		double theta = 0;
		double delta = Math.PI*2 / verticies;
		Random ran = new Random();
		for(int i = 0; i < verticies; i++) {
			int len = ran.nextInt(mass/2)+mass/2;
			xpoints[i] = (int) (len * Math.cos(theta));
			ypoints[i] = (int) (len * Math.sin(theta));
			theta+=delta;
		}
		this.model = new Polygon(
				xpoints, 
				ypoints, 
				verticies);	
	}
	
	public boolean update(double fD) {
		if(terminated) return true;
		
		if(collitionProtection >= timeAlive) {
			timeAlive+= fD;
		}
		
		if(!alive) {
			if(mass < 25)return true;
			
			
			Random ran = new Random();
			double theta = ran.nextFloat()*Math.PI*2 + Math.PI/2;
			
			ms.addMetheor(new Vector2D(pos),
					new Vector2D(
							Math.cos(theta),
							Math.sin(theta)
							)
					, mass/2);
			
			ms.addMetheor(new Vector2D(pos),
					new Vector2D(
							Math.cos(theta+ Math.PI),
							Math.sin(theta+ Math.PI)
							)
					, mass/2);
			
			
			return true;
		}
		
		if(pos.getX() < -Window.width || pos.getX() > Window.width*2
				|| pos.getY() < -Window.height || pos.getY() > Window.height*2)
			return true;
		
		
		pos.add(vel);
		
		if(inside) {
			List<Metheor> metheors = ms.getMetheors();
			
			Metheor obj = null;
			boolean inters = false;
			
			for(int i = 0; i < metheors.size(); i++) {
				obj = metheors.get(i);
				if(!obj.equals(this) && obj.hasCollisionProtection()) {
					if(obj.intersects(
							new Rectangle((int)pos.getX()-mass/4, (int)pos.getY()-mass/4, mass/2, mass/2))
							) {
						inters = true;
						break;
					}
				}
			}
			
			if(inters) {
				
				
				Metheor target = this.getMass()>obj.getMass() ? obj : this;
				Metheor partent = this.getMass()<=obj.getMass() ? obj : this;
				
				int newmass = (partent.mass + target.getMass()/3);
				if(newmass > 100)newmass = 100;
				
				target.terminate();
				partent.setMass(newmass);
				partent.generateModel(10);
			}
		}
		
		
		return false;
	}
	
	public void bounce(int wall) {
		if(inside) {
			if(wall == 0 || wall == 2) {
				vel.getComp()[0]*=-1;
			}
			if(wall == 1 || wall == 3) {
				vel.getComp()[1]*=-1;
			}
		}
	}
	
	public void damage() {
		if(spawnProtection < timeAlive) {
			this.alive = false;
		}
	}
	
	public void render(Graphics2D g) {
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		g.translate(x, y);
		
		
		g.setColor(new Color(0.1f,0.1f,0.1f,1f));
		g.fill(model);
		g.setColor(Color.white);
		g.draw(model);
		
		g.translate(-x, -y);	
		
	}
	
	public boolean intersects(Rectangle rec) {
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		this.model.translate(x, y);
		boolean intersection = this.model.intersects(rec);
		this.model.translate(-x, -y);
		return intersection;
	}
	
	public int getMass() {
		return mass;
	}
	public Vector2D getPos() {
		return pos;
	}
	public Vector2D getVel() {
		return vel;
	}
	public void setInside(boolean bool) {
		this.inside = bool;
	}
	public void terminate() {
		this.terminated = true;
	}
	public boolean hasSpawnProtection() {
		return spawnProtection < timeAlive;
	}
	public boolean hasCollisionProtection() {
		return collitionProtection < timeAlive;
	}
	public void setMass(int mass) {
		this.mass = mass;
	}
	
}
