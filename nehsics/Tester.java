package nehsics;
import static nehsics.math.Util.*;
import java.awt.event.*;
import javax.swing.*;

public class Tester {
	protected JFrame frame;
	protected Display display;
	protected World world;
	protected static double SPEED = 1;
	protected static double FPS = 60;
	protected static int PRECISION = 5;

	public Tester() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setSize(500,500);
		frame.setTitle("NEHsics");
		frame.addKeyListener(new KeyAdapter() {
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
					case '0': display.zoomDefault(); display.x = display.y = 0; return;
				}
			}
		});
		display = new Display(frame);
		world = new World();
		setup();
		Timer timer = new Timer(FPS);
		while(true) {
			double dt = timer.tick();
			for (int i=0; i < PRECISION; i++)
				world.step(SPEED*dt/PRECISION);
			update(dt);
			display.drawWorld(world);
			display.show();
		}
	}

	protected void update(double dt) {

	}

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

	public static void main(String[] args) {
		new Tester();
	}
}
