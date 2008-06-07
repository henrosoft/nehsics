package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.*;

public abstract class Body {
	// tmp forces will be cleared after each step!
	protected Set<Vector2d> tmp = new HashSet<Vector2d>();
	protected Set<Vector2d> forces = new HashSet<Vector2d>();
	protected double mass; // kg
	protected double charge; // coulombs
	protected double radius;
    protected List<Body> alreadyHit = new LinkedList<Body>();
	protected Vector2d position; // m (pixels)
	protected Vector2d velocity; // m/s
	protected Shape shape;
	protected boolean visible = true;
	public Body(Vector2d pos, Vector2d vel, double m, Shape s) {
		position = pos;
		velocity = vel;
		mass = m;
		shape = s;
	}
	public void setVisible(boolean v)
	{
		visible = v;
	}
	public void setRadius(double r) {
		radius = r;
	}

	public void step(double dt) { // seconds
		Vector2d accel = v(), disp = v();
		for (Vector2d f : forces)
			accel = add(accel, scale(f, 1/mass));
		for (Vector2d f : tmp)
			accel = add(accel, scale(f, 1/mass));
		velocity = add(velocity, scale(accel, dt));
		position = add(position, scale(velocity, dt));
		alreadyHit.clear();
		tmp.clear();
	}

	public void applyForce(Vector2d f) {
		tmp.add(f);
	}

	public void addForce(Vector2d f) {
		forces.add(f);
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double m) {
		mass = m;
	}

	public double getCharge() {
		return charge;
	}

	public void setCharge(double c) {
		charge = c;
	}

	public Vector2d getPosition() {
		return position;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2d v) {
		velocity = v;
	}

	public void setPosition(Vector2d position) {
		this.position = position;
	}

	public void paint(Graphics2D g2d) {
		if(!visible)
			return;
		g2d.setColor(Color.BLACK);
		AffineTransform af = AffineTransform.getTranslateInstance(
			position.getX()-radius, position.getY()-radius);
		Shape transformed = af.createTransformedShape(shape);
		g2d.fill(transformed);
	}

	public Set<Vector2d> getForces() {
		return forces;
	}
}
