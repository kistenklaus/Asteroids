package game;

import java.awt.Graphics2D;

import engine.Engine;
import engine.GameContainer;
import game.starship.Starship;

public class Astroits implements GameContainer{
	
	private Starship starship;
	private MetheorSystem ms;
	
	@Override
	public void init(Engine engine) {
		
		this.ms = new MetheorSystem();
		this.starship = new Starship(engine.getInput(), ms);
		
		System.out.println("initialized Astroits");
	}

	@Override
	public void update(double fD) {
		starship.update(fD);
		ms.update(fD);
	}
	
	@Override
	public void render(Graphics2D g) {
		starship.render(g);
		ms.render(g);
	}
	
	
	public static void main(String[] args){
		Engine engine = new Engine(new Astroits(), 640, 640);
		engine.start();
	}
}
