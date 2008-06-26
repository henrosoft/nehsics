package nehsics.world;
import nehsics.bodies.Body;
import java.awt.Graphics2D;
import java.util.*;

public class World {
	private final List<Body> bodies = new ArrayList<Body>();
	private final List<WorldListener> listeners = new LinkedList<WorldListener>();
	public static Object lock = new Object();

	public void addBody(Body b) {
		synchronized (lock) {
			bodies.add(b);
			for (WorldListener listener : listeners)
				listener.newBody(bodies, b);
		}
	}

	public Body[] copyBodies() {
		Body[] out = new Body[bodies.size()];
		bodies.toArray(out);
		return out;
	}

	public void addListener(WorldListener l) {
		synchronized (lock) {
			listeners.add(l);
		}
	}

	public void step(double dt) {
		synchronized (lock) {
			for (WorldListener listener : listeners)
				listener.beginStep(bodies, dt);
			for (Body body : bodies)
				body.step(dt);
			for (WorldListener listener : listeners)
				listener.endStep(bodies, dt);
		}
	}

	public void paint(Graphics2D g2d) {
		synchronized (lock) {
			for (WorldListener listener : listeners)
				listener.paint(g2d);
			for (Body body : bodies)
				body.paint(g2d);
		}
	}
}
