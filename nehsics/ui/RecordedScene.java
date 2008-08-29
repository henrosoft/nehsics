package nehsics.ui;
import nehsics.world.*;
import nehsics.ui.Timer;
import java.awt.*;
import java.awt.event.*;

public abstract class RecordedScene extends Scene {
	protected Canvas canvas;
	protected Display display;
	protected StateTracker tracker;
	protected World world;
	protected Timer timer = new Timer(60);
	private final static double dt = .016;
	private boolean reset, save;
	protected double SPEED = 1, PRECISION = 100;
	public final static String NAME = "RECORDED SCENE";

	public RecordedScene(Canvas c) {
		canvas = c;
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
					case 's': save = true; return;
					case 'r': reset = true; return;
				}
			}
		});
		display = new Display(c);
		reset();
	}

	public void save() {
		tracker.save();
		try {
			quit();
		} catch (Exception e) {}
	}

	private void reset() {
		reset = false;
		world = new World();
		tracker = new StateTracker();
		world.addListener(tracker);
		display.reset();
		setupDisplay();
		setup();
	}

	public void run() {
		while (running) {
			if (reset)
				reset();
			if (save)
				save();
			display.clear();
			update(dt);
			preWorld();
			world.paint(display.getGraphics());
			postWorld();
			display.unsetBufTransforms();
			overlay();
			display.show();
			for (int i=0; i < PRECISION; i++) {
				timer.tick(); // ignoring returned dt here
				world.step(SPEED*dt/PRECISION);
			}
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
