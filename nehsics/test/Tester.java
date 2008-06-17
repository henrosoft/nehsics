package nehsics.test;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.ui.Timer;
import nehsics.bodies.*;
import static nehsics.math.Util.*;
import java.awt.*;
import java.awt.event.*;

public class Tester extends Test {
	protected volatile boolean running = true;
	protected Canvas canvas;
	protected Display display;
	protected World world;
	protected Timer timer = new Timer(60); // 60fps
	private boolean reset, reverseTime; // doesn't work as advertised
	protected double speedModifier = 1; // user adjusted
	protected double SPEED = 1, PRECISION = 1; // not really constants
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
					case 'h': display.move(v(10,0)); return;
					case 'l': display.move(v(-10,0)); return;
					case 'k': display.move(v(0,10)); return;
					case 'j': display.move(v(0,-10)); return;
				}
				switch (e.getKeyChar()) {
					case 'r': reset = true; return;
					case 'f':
						display.setFadeEnabled(!display.getFadeEnabled()); return;
					case '!': reverseTime = !reverseTime; return;
					
					case 'w': speedModifier *= 1.2; return;
					case 'x': speedModifier /= 1.2; return;
					case 's': speedModifier = 1; return;
					
					case '+': case '=': display.zoomIn(); return;
					case '-': display.zoomOut(); return;
					case '0': display.softReset(); return;
					case 'q': display.trackPrevious(); return;
					case 'a': display.trackNext(); return;
				}
			}
		});
		display = new Display(c);
		reset();
	}

	private void reset() {
		reset = false;
		reverseTime = false;
		world = new World();
		display.reset();
		speedModifier = 1;
		setupDisplay();
		setup();
	}

	public void run() {
		while (running) {
			if (reset)
				reset();
			double dt = (reverseTime ? -1 : 1)*timer.tick();
			for (int i=0; i < PRECISION; i++)
				world.step(SPEED*speedModifier*dt/PRECISION);
			display.clear();
			update(dt);
			preWorld();
			world.paint(display.getGraphics());
			postWorld();
			display.unsetBufTransforms();
			overlay();
			display.show();
		}
	}

	protected void setupDisplay() {
		world.addListener(display);
	}

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
		PRECISION = 5;

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
