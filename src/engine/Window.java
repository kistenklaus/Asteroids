package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window implements Runnable{
	
	public static int width, height;
	
	private JFrame jframe;
	private Thread thread;
	private Engine engine;
	private BufferStrategy bs;
	
	private boolean running;
	
	public Window(Engine engine, Input input) {
		jframe = new JFrame("");
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(Window.width >= 1920 && Window.height >= 1080)
			jframe.setUndecorated(true);
		jframe.setVisible(true);
		jframe.setResizable(false);
		
		
		this.engine = engine;
		jframe.add(engine);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		
		
		
		jframe.addKeyListener(input);
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		this.thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		System.out.println("Render-Thread stoped");
	}
	
	@Override
	public void run() {
		System.out.println("starting Render-Thread ...");
		while(running) {
			if(bs == null) {
				engine.createBufferStrategy(2);
			}
			
			this.bs = engine.getBufferStrategy();
			
			Graphics g = bs.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, jframe.getWidth(), jframe.getHeight());
			
			engine.render((Graphics2D) g);
			
			g.dispose();
			bs.show();
		}
		this.stop();
	}
}
