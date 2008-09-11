package nehsics.world;

import java.awt.*;

import java.awt.geom.AffineTransform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nehsics.bodies.*;

import nehsics.math.*;
import static nehsics.math.Util.*;

public class StateTracker extends WorldAdapter implements java.io.Serializable {
	public final static long serialVersionUID = 10847197493174L;
	Map<Integer,StateStream> tracker = new HashMap<Integer,StateStream>();
	public static boolean DISABLE_EXTRAPOLATION;
	private double scale, time; // ms
	private boolean finalize, init = true;

	public StateTracker(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return scale;
	}

	// finalize = at next step, snapshot ALL objects - prepare for clean save
	public void finalize() {
		finalize = true;
	}

	public void endStep(List<Body> bodies, double dt) {
		time += dt;
		StateStream t;
		for (Body b : bodies) {
			t = tracker.get(b.hashCode());
			if (t == null)
				tracker.put(b.hashCode(),  t = new StateStream(b, scale, time, init));
			t.update(time, b, finalize);
		}
		init = false;
	}

	public void reset() {
		for (Integer key : tracker.keySet())
			tracker.get(key).reset();
	}

	public void save(File output) throws IOException {
		new ObjectOutputStream(
			new FileOutputStream(output)
		).writeObject(this);
	}

	public void paint(double time, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		for (Integer key : tracker.keySet()) {
			StateStream stream = tracker.get(key);
			Vector2d position = stream.next(time);
			if (position == null)
				continue;
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

class StateStream implements java.io.Serializable {
	public final static long serialVersionUID = 8882384918741L;
	private final static double dpixel_threshold = 5;
	private final Shape shape;
	private final double radius, min_delta;
	private ArrayList<State> positions = new ArrayList<State>();
	private int mark;
	private State last;

	private class State implements java.io.Serializable {
		public final static long serialVersionUID = 7711883939421L;
		final double time;
		final Vector2d pos, vel;
		State(double time, Body b) {
			this.time = time;
			this.pos = b.getPosition();
			this.vel = b.getVelocity();
		}
	}

	public StateStream(Body body, double scale, double time, boolean init) {
		min_delta = dpixel_threshold / scale;
		shape = body.getShape();
		radius = body.getRadius();
		positions.add(last = new State(init ? 0 : time, body));
	}

	public void update(double time, Body b, boolean finalize) {
		if (finalize || isDiff(time, b.getPosition()) || !sameDirection(b.getPosition()))
				positions.add(last = new State(time, b));
	}

	public Shape getShape() {
		return shape;
	}

	public double getRadius() {
		return radius;
	}

	private static Vector2d extrapolate_linear(State prev, State next, double percent) {
		return add(prev.pos, scale(sub(next.pos, prev.pos), percent));
	}

	private static Vector2d extrapolate_quad(State prev, State next, double time) {
		if (StateTracker.DISABLE_EXTRAPOLATION)
			return prev.pos;
		assert time > prev.time && time < next.time;
		Vector2d a = scale(sub(next.vel, prev.vel), 1/(next.time - prev.time));
		return add(prev.pos,
			scale(sub(prev.vel, scale(a, prev.time)), (time - prev.time)),
			scale(scale(a, .5), (Math.pow(time, 2) - Math.pow(prev.time, 2)))
		);
	}

	// "at least it works" extrapolation
	private static Vector2d extrapolate(State prev, State next, double time) {
		double percent = (time - prev.time) / (next.time - prev.time);
		Vector2d linear = extrapolate_linear(prev, next, percent);
		Vector2d quad = extrapolate_quad(prev, next, time);
		return add(scale(linear, percent), scale(quad, 1 - percent));
	}

	/**
	 * @return null if body does not exist at the point else position
	 */
	public Vector2d next(double time) {
		State ret = positions.get(mark);
		if (ret.time < time) {
			while (ret.time < time && mark < positions.size() - 1)
				ret = positions.get(++mark);
		} else {
			if (mark == 0 && ret.time != time)
				return null;
			while (ret.time > time && mark > 0)
				ret = positions.get(--mark);
		}
		Vector2d pos = ret.pos;
		if (ret.time > time && mark > 0)
			pos = extrapolate(positions.get(mark-1), ret, time);
		else if (ret.time < time && mark < positions.size() - 1)
			pos = extrapolate(ret, positions.get(mark+1), time);
		return pos;
	}

	public void reset() {
		mark = 0;
	}

	private boolean isDiff(double time, Vector2d test) {
		double dt = time - last.time;
		Vector2d estimated = add(last.pos, scale(last.vel, dt));
		return distance(estimated, test) > min_delta;
	}

	private boolean sameDirection(Vector2d test) {
		return sign(last.pos.getX()) == sign(test.getX()) && sign(last.pos.getY()) == sign(test.getY());
	}
}
