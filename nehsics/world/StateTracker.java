package nehsics.world;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import java.awt.geom.*;

import nehsics.bodies.*;
import nehsics.math.*;
import static nehsics.math.Util.*;

// TODO *really* make serializable
public class StateTracker extends WorldAdapter implements java.io.Serializable {
	public final static long serialVersionUID = 10847197493174L;
	Map<Body,StateStream> tracker = new HashMap<Body,StateStream>();
	private double time; // ms

	public void endStep(List<Body> bodies, double dt) {
		time += dt;
		for (Body b : bodies)
			tracker.get(b).update(time, b.getPosition());
	}
	
	public void newBody(List<Body> bodies, Body b) {
		tracker.put(b,  new StateStream(b));
	}

	public void save() {
		throw new UnsupportedOperationException(); // TODO
	}

	// TODO use stream's jump() if they skip backwards or forwards too much
	public void paint(double time, Graphics2D g2d) {
		for (Body key : tracker.keySet()) {
			StateStream stream = tracker.get(key);
			Vector2d position = stream.next(time);
			Shape shape = stream.getShape();
			double radius = stream.getRadius();
			AffineTransform af = AffineTransform.getTranslateInstance(
				position.getX()-radius, position.getY()-radius);
			Shape transformed = af.createTransformedShape(shape);
			g2d.fill(transformed);
		}
	}

	public double length() {
		return time;
	}
}

class StateStream {
	private ArrayList<State> positions = new ArrayList<State>();
	private int mark;
	private Shape shape;
	private double radius;

	private class State {
		double time;
		Vector2d vec;
		State(double time, Vector2d vec) {
			this.time = time;
			this.vec = vec;
		}
	}

	public StateStream(Body body) {
		shape = body.getShape();
		radius = body.getRadius();
	}

	// TODO store only significant differences in position
	// (ask display for 0.5px equivalent in dpos)
	public void update(double time, Vector2d pos) {
		positions.add(new State(time, pos));
	}

	public Shape getShape() {
		return shape;
	}

	public double getRadius() {
		return radius;
	}

	public Vector2d next(double time) throws IndexOutOfBoundsException {
		State ret = positions.get(mark++);
		while (ret.time < time)
			ret = positions.get(mark++);
		return ret.vec;
	}

	public Vector2d jump(double time) throws IndexOutOfBoundsException {
		throw new UnsupportedOperationException(); // TODO
	}
}
