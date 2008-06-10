package nehsics;
import static nehsics.math.Util.*;
import java.awt.*;
import java.awt.event.*;

public class Tester extends Test {
	protected volatile boolean running = true;
	private final static Font f = new Font("Serif", Font.PLAIN, 12);
	protected Canvas canvas;
	protected Display display;
	protected World world;
	protected Timer timer;
	protected double SPEED = 1;
	protected double speed = 1;
	protected double FPS = 60;
	private boolean sign = true;
	protected int PRECISION = 5;
	protected int following = -1;
	public final static String NAME = "Falling Spheres";

	public void quit() {
		running = false;	
	}

	public Tester(Canvas c) {
		canvas = c;
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						display.setX(display.getX() + 10);
						return;
					case KeyEvent.VK_RIGHT:
						display.setX(display.getX() - 10);
						return;
					case KeyEvent.VK_UP:
						display.setY(display.getY() + 10);
						return;
					case KeyEvent.VK_DOWN:
						display.setY(display.getY() - 10);
						return;
				}
				switch (e.getKeyChar()) {
					case 'r': reset(); return;
					case 'f': display.setFadeEnabled(!display.getFadeEnabled()); return;
					case '!': sign = !sign; return;
					case 'k': speed *= 1.2; return;
					case 'h': speed /= 1.2; return;
					case 'j': speed = 1; return;
					case '+': case '=': display.zoomIn(); return;
					case '-': display.zoomOut(); return;
					case '0': display.zoomDefault(); following = -1;
							display.setX(0); display.setY(0); return;
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

	private void reset() {
		world = new World();
		display.reset();
		speed = 1;
		sign = true;
		following = -1;
		setup();
	}

	public void run() {
		while (running) {
			double dt = (sign ? 1 : -1)*timer.tick();
			for (int i=0; i < PRECISION; i++)
				world.step(SPEED*speed*dt/PRECISION);
			update(dt);
			preWorld();
			display.drawWorld(world);
			postWorld();
			display.show();
		}
	}

	protected void update(double dt) {
		if (following >= 0) {
			Body fBody = world.getBodyFromIndex(following);
			double fX = fBody.getPosition().getX();
			double fY = fBody.getPosition().getY();
			display.centerDisplay(fX, fY);
		}
	}

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
