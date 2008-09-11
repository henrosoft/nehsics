package nehsics.ui;

import java.awt.Canvas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import nehsics.world.*;

import static nehsics.math.Util.*;

public class Player extends Scene {
	Canvas canvas;
	public final static String NAME = "-> Load Recording";
	Display display;
	StateTracker tracker;
	double speedModifier = 1;
	boolean reset, reverseTime;

	public Player(Canvas c) throws IOException, ClassNotFoundException {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(c);
		File selected = fc.getSelectedFile();
		canvas = c;
		c.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
					case 'h': display.move(v(10,0)); return;
					case 'l': display.move(v(-10,0)); return;
					case 'k': display.move(v(0,10)); return;
					case 'j': display.move(v(0,-10)); return;
					case 'e': StateTracker.DISABLE_EXTRAPOLATION = !StateTracker.DISABLE_EXTRAPOLATION; return;
					case 'r': reset = true; return;
					case 'f': display.toggleFade(); return;
					case 'w': speedModifier *= 1.2; return;
					case 'x': speedModifier /= 1.2; return;
					case '!': reverseTime = !reverseTime; return;
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
		if (selected == null)
			tracker = new StateTracker(1);
		else
			tracker = (StateTracker)(new ObjectInputStream(
				new FileInputStream(selected)).readObject());
		display.setScale(tracker.getScale());
	}

	private void reset() {
		reset = false;
		tracker.reset();
	}

	public void run() {
		double time = 0;
		double max = tracker.length();
		Timer timer = new Timer(60);
		while (running) {
			if (reset) {
				reset();
				time = 0;
			}
			tracker.paint(time, display.getGraphics());
			time += .01*speedModifier*(reverseTime ? -1 : 1);
			time = Math.max(0, Math.min(max, time));
			display.show();
			timer.tick();
			display.clear();
		}
	}
}
