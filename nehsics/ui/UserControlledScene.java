package nehsics.ui;

import java.awt.*;

import java.awt.event.*;

import java.io.File;

import javax.swing.JFileChooser;

import nehsics.ui.Timer;

import nehsics.world.*;

import static nehsics.math.Util.*;

public abstract class UserControlledScene extends Scene {
	protected Canvas canvas;
	protected Display display;
	protected World world;
	protected StateTracker tracker;
	protected Timer timer = new Timer(60); // 60fps
	protected final static double DT = .016;
	private boolean reset, rtoggle, reverseTime; // doesn't work as advertised
	protected double speedModifier = 1; // user adjusted
	protected double SPEED = 1, PRECISION = 1; // not really constants
	public final static String NAME = "Falling Spheres";

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
					case ' ': rtoggle = true; return;
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
		if (tracker != null)
			toggleRecord();
		world = new World();
		display.reset();
		speedModifier = 1;
		setupDisplay();
		setup();
	}

	private void toggleRecord() {
		rtoggle = false;
		if (tracker == null) {
			tracker = new StateTracker(display.getScale());
			world.addListener(tracker);
		} else {
			tracker.finalize();
			world.step(DT);
			JFileChooser fc = new JFileChooser();
			fc.showSaveDialog(canvas);
			File selected = fc.getSelectedFile();
			try {
				tracker.save(selected);
			} catch (Exception e) {
			} finally {
				world.removeListener(tracker);
				tracker = null;
			}
		}
	}

	public void run() {
		while (running) {
			if (reset)
				reset();
			if (rtoggle)
				toggleRecord();
			timer.tick();
			double dt = DT * (reverseTime ? -1 : 1);
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

	protected void overlay() {
		if (tracker != null) {
			Graphics2D g2d = display.getGraphics();
			g2d.setColor(Color.RED);
			g2d.drawString("RECORDING", 8, 20);
		}
	}

	protected void update(double dt) {}
	protected void preWorld() {}
	protected void postWorld() {}
	protected void setup() {}
}
