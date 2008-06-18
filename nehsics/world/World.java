package nehsics.world;
import nehsics.bodies.Body;
import java.awt.Graphics2D;
import java.util.*;

public class World {
	protected ArrayList<Body> bodies = new ArrayList<Body>();
	protected List<WorldListener> listeners = new LinkedList<WorldListener>();

	public void addBody(Body b) {
		bodies.add(b);
		for (WorldListener listener : listeners)
			listener.newBody(this, b);
	}

	public void addListener(WorldListener l) {
		listeners.add(l);
	}

	public void step(double dt) {
		for (WorldListener listener : listeners)
			listener.beginStep(this, dt);
		for (Body body : bodies)
			body.step(dt);
		for (WorldListener listener : listeners)
			listener.endStep(this, dt);
	}

	public void paint(Graphics2D g2d) {
		for (WorldListener listener : listeners)
			listener.paint(g2d);
		for (Body body : bodies)
			body.paint(g2d);
	}
}
