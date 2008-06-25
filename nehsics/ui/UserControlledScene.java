package nehsics.ui;
import nehsics.world.*;
import nehsics.ui.Timer;
import static nehsics.math.Util.*;
import java.awt.*;
import java.awt.event.*;

public abstract class UserControlledScene extends Scene {
	protected Canvas canvas;
	protected Display display;
	protected World world;
	protected Timer timer = new Timer(60); // 60fps
	private boolean reset, reverseTime; // doesn't work as advertised
	protected double speedModifier = 1; // user adjusted
	protected double SPEED = 1, PRECISION = 1; // not really constants
	public final static String NAME = "Falling Spheres";

	// no guarantees if the user tampers with the controls
	private static final boolean BE_DETERMINISTIC = true;

	public UserControlledScene(Canvas c) {
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
					case 'f': display.toggleFade(); return;
					case '!': reverseTime = !reverseTime; return;
					case 'w': speedModifier *= 1.2; return;
					case 'x': speedModifier /= 1.2; return;
					case 's': speedModifier = 1; return;
					case '+':
					case '=': display.zoomIn(); return;
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
			double dt = timer.tick();
			if (BE_DETERMINISTIC)
				dt = .016;
			dt *= reverseTime ? -1 : 1;
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
	protected void setup() {}
}
