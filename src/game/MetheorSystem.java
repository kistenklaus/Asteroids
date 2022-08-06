package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import engine.Window;
import engine.math.Vector2D;
import game.particleSystem.NonFixedPS;

public class MetheorSystem {
	
	private ArrayList<Metheor> metheors;
	private NonFixedPS ps;
	private float timer, spawnrate;
	private Rectangle[] boundings;
	
	public MetheorSystem() {
		this.metheors = new ArrayList<>();
		this.spawnrate = 2.5f;
		this.timer = spawnrate * 2;
		this.ps = new NonFixedPS();
		this.boundings = new Rectangle[] {
				new Rectangle(-Window.width, -Window.height, Window.width, Window.height*3),
				new Rectangle(-Window.width, -Window.height, Window.width*3, Window.height),
				new Rectangle(Window.width, -Window.height, Window.width, Window.height*3),
				new Rectangle(-Window.width, Window.height, Window.width*3, Window.height)
		};
	}
	
	public void update(double fD) {
		
		timer+=fD;
		if(timer > spawnrate) {
			timer -= spawnrate;
			createMetheor();
		}
		
		for(int i = 0; i < metheors.size(); i++) {
			Metheor obj = metheors.get(i);
			boolean intersection = false;
			for(int j = 0; j < boundings.length; j++) { 
				if(obj.intersects(boundings[j])) {
					obj.bounce(j);
					intersection = true;
				}
			}
			if(!intersection) {
				obj.setInside(true);
			}
			
			if(obj.update(fD)) {
				ps.explosion(new Vector2D(obj.getPos()), obj.getMass(), obj.getMass());
				metheors.remove(i);
				i--;
			}
		}
		
		ps.update(fD);
	}
	
	private void createMetheor() {
		
		Random ran = new Random();
		double theta = ran.nextFloat()* Math.PI*2;
		Vector2D pos = new Vector2D((float)Window.width*0.8f*Math.cos(theta)+ Window.width/2,
				(float)Window.width*0.8f*Math.sin(theta) + Window.height/2);
		
		
		Vector2D des = new Vector2D(ran.nextInt(Window.width), ran.nextInt(Window.height));
		des.sub(pos);
		des.normaize();
		
		Vector2D vel = new Vector2D(des);
		
		metheors.add(new Metheor(pos, vel, 50, this));
	}
	
	public void addMetheor(Vector2D pos, Vector2D vel, int mass) {
		metheors.add(new Metheor(pos, vel, mass, this));
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < metheors.size(); i++) {
			Metheor metheor = metheors.get(i);
			if(metheor != null) {
				metheor.render(g);
			}
		}
		ps.render(g);
	}
	
	public boolean intersects(Rectangle rec) {
		for(int i = 0; i < metheors.size(); i++) {
			if(metheors.get(i).intersects(rec)) {
				return true;
			}
		}
		return false;
	}
	
	public Metheor intersectsWith(Rectangle rec) {
		for(int i = 0; i < metheors.size(); i++) {
			if(metheors.get(i).intersects(rec)) {
				return metheors.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Metheor> getMetheors(){
		return metheors;
	}
}




















