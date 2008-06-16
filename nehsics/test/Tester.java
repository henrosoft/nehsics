package nehsics.test;
import nehsics.force.*;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.ui.Timer;
import nehsics.bodies.*;
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

	public static void main(String[] args) {
		new Starter(NAME);
	}

	public void quit() {
		running = false;	
	}

	public Tester(Canvas c) {
		canvas = c;
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
					case 'h':
						display.setX(display.getX() + 10);
						return;
					case 'l':
						display.setX(display.getX() - 10);
						return;
					case 'k':
						display.setY(display.getY() + 10);
						return;
					case 'j':
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
						display.setX(0); display.setY(0);
						display.setTrackedBody(null); setupDisplay(); return;
//					case 'q': display.setX(0); display.setY(0);
//						display.setTrackedBody(
//							world.getBodyFromIndex(
//							following = world.nextBodyIndex(following))
//						); return;
//					case 'a': display.setX(0); display.setY(0);
//						display.setTrackedBody(
//							world.getBodyFromIndex(
//							following = world.prevBodyIndex(following))
//						); return;
					case 'v': showVelocity = !showVelocity; return;
				}
			}
		});
		display = new Display(c);
		timer = new Timer(FPS);
		reset();
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
				
//			if (following >= 0) {
//				Body fBody = world.getBodyFromIndex(following);
//				if (showVelocity)
//				   g2d.drawString("" + fBody.getVelocity().length(), 100, 100);
//			}
			
			preWorld();
			world.paint(display.getGraphics());
			postWorld();
			display.unsetBufTransforms();
			overlay();
			display.show();
		}
	}

	protected void setupDisplay() {}
	protected void update(double dt) {}
	protected void preWorld() {}
	protected void postWorld() {}
	protected void overlay() {}

	protected void setup() {
		FieldManager f = new FieldManager();
		Stats s = new Stats();
		world.addListener(s);
		world.addListener(f);
		world.addListener(new Gravitation(f));
		world.addListener(new Collider(s));
		SPEED = 5;
		PRECISION = 15;

		// large circles... (low density here)
		Circle a = new Circle(45,10);
		a.setPosition(v(-110,-150));

		// fall as fast as small circles (high density here)	
		Circle b = new Circle(15,500);
		b.setPosition(v(-210,-150));
		b.setVelocity(v(1,0));

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
