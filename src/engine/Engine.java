package engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class Engine extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 4454051830198488462L;
	
	private GameContainer gc;
	private Thread thread;
	private Input input;
	private Window window;
	
	private boolean running;
	
	public Engine(GameContainer gc, int width, int height) {
		this.gc = gc;
		Dimension d = new Dimension(width, height);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
		
		this.input = new Input();
		this.addKeyListener(input);
		Window.width = width;
		Window.height = height;
		this.window = new Window(this, input);
		
		gc.init(this);
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		this.thread = new Thread(this);
		thread.start();
		window.start();
	}
	
	public synchronized void stop() {
		window.stop();
		if(!running)
			return;
		running = false;
		System.out.println("Upg-Thread stoped");
		System.exit(1);
	}

	@Override
	public void run() {
		long lastFrame = System.nanoTime();
		double timer = 0;
		System.out.println("starting Upd-Thread ...");
		while(running) {
			long currFrame = System.nanoTime();
			double delta = (currFrame - lastFrame)/1000000000d;
			lastFrame = currFrame;
			timer += delta;
			if(timer > 1d/60d) {
				gc.update(timer);
				timer = 0;
			}
			
			if(input.isKeyDown(Input.ESCAPE)) {
				this.stop();
			}
		}
		this.stop();
	}
	
	public void render(Graphics2D g) {
		gc.render(g);
	}
	
	public Input getInput() {
		return input;
	}
	
	
	
	
	
}
