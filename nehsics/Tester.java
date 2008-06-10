package nehsics;
import static nehsics.math.Util.*;
import java.awt.*;
import java.awt.event.*;

public class Tester extends Thread {
	private volatile boolean running = true;
	protected Canvas canvas;
	protected Display display;
	protected World world;
	protected Timer timer;
	protected static double SPEED = 1;
	protected static double FPS = 60;
	protected static int PRECISION = 5;
	protected int following = -1;

	public void quit() {
		running = false;	
	}

	public Tester(Canvas c) {
		canvas = c;
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT: display.x += 10; return;
					case KeyEvent.VK_RIGHT: display.x -= 10; return;
					case KeyEvent.VK_UP: display.y += 10; return;
					case KeyEvent.VK_DOWN: display.y -= 10; return;
				}
				switch (e.getKeyChar()) {
					case '+': case '=': display.zoomIn(); return;
					case '-': display.zoomOut(); return;
					case '0': display.zoomDefault(); following = -1; display.x = display.y = 0; return;
					case 'q': following = world.nextBodyIndex(following); return;
					case 'a': following = world.prevBodyIndex(following); return;
				}
			}
		});
		display = new Display(c);
		world = new World();
		setup();
		timer = new Timer(FPS);
	}

	public void run() {
		while (running) {
			double dt = timer.tick();
			if (following >= 0) {
				Body fBody = world.getBodyFromIndex(following);
				double fX = fBody.getPosition().getX();
				double fY = fBody.getPosition().getY();
				display.centerDisplay(fX, fY);
			}
			for (int i=0; i < PRECISION; i++)
				world.step(SPEED*dt/PRECISION);
			update(dt);
			preWorld();
			display.drawWorld(world);
			postWorld();
			display.show();
		}
	}

	protected void update(double dt) {}
	protected void preWorld() {}
	protected void postWorld() {}

	protected void setup() {

		// large circles...
		Circle a = new Circle(45,900);
		a.setPosition(v(-110,-150));

		// fall as fast as small circles	
		Circle b = new Circle(15,150);
		b.setPosition(v(-210,-150));

		// normal force opposes gravity
		Circle stationary = new Circle(40,10);
		stationary.setPosition(v(0,-150));
		stationary.addForce(v(0,-SURFACE_G*stationary.getMass()));

		// earth is down there (its the floor)
		Circle earth = new Circle(EARTH_RADIUS, EARTH_MASS);
		earth.setPosition(v(0,EARTH_RADIUS+150));

		world.addBody(a);
		world.addBody(b);
		world.addBody(stationary);
		world.addBody(earth);
	}
}
