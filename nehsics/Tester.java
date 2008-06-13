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
	protected boolean showVelocity = false;
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
					case 'f':
						display.setFadeEnabled(!display.getFadeEnabled()); return;
					case '!': sign = !sign; return;
					
					case 'w': speed *= 1.2; return;
					case 'x': speed /= 1.2; return;
					case 's': speed = 1; return;
					
					case '+': case '=': display.zoomIn(); return;
					case '-': display.zoomOut(); return;
					case '0': display.zoomDefault(); following = -1;
						display.setX(0); display.setY(0); display.setTrackedBody(null); return;
					case 'q': display.setX(0); display.setY(0);
						display.setTrackedBody(
							world.getBodyFromIndex(following = world.nextBodyIndex(following))
						); return;
					case 'a': display.setX(0); display.setY(0);
						display.setTrackedBody(
							world.getBodyFromIndex(following = world.prevBodyIndex(following))
						); return;
					case 'v': showVelocity = !showVelocity; return;
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
			Graphics2D g2d = display.getGraphics();
			g2d.setColor(Color.RED);
			g2d.setFont(f);
				
			double dt = (sign ? 1 : -1)*timer.tick();
			for (int i=0; i < PRECISION; i++)
				world.step(SPEED*speed*dt/PRECISION);
			update(dt);
			display.clear();
				
			if (following >= 0) {
				Body fBody = world.getBodyFromIndex(following);
				if (showVelocity)
				   g2d.drawString("" + fBody.getVelocity().length(), 100, 100);
			}
			
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
		SPEED = 5;
		PRECISION = 15;

		// large circles... (low density here)
		Circle a = new Circle(45,10);
		a.setPosition(v(-110,-150));

		// fall as fast as small circles (high density here)	
		Circle b = new Circle(15,500);
		b.setPosition(v(-210,-150));

		// normal force opposes gravity
		Circle stationary = new Circle(40,10);
		stationary.setPosition(v(0,-150));
		stationary.addForce(v(0,-SURFACE_G*stationary.getMass()));
		
		world.addField(new ResistiveForce(1));

		// earth is down there (its the floor)
		Circle earth = new Circle(EARTH_RADIUS, EARTH_MASS);
		earth.setPosition(v(0,EARTH_RADIUS+150));

		world.addBody(a);
		world.addBody(b);
		world.addBody(stationary);
		world.addBody(earth);
	}
}
