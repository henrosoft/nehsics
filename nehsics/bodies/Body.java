package nehsics.bodies;
import nehsics.world.*;
import nehsics.force.*;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.awt.geom.*;
import java.awt.*;
public abstract class Body {
	private static long nextGroup = System.nanoTime();
	protected long group = nextGroup++;
	// tmp forces will be cleared after each step!
	protected Set<Vector2d> tmp = new HashSet<Vector2d>();
	protected Set<BindingForce> bonds = new HashSet<BindingForce>();
	protected Set<Body> bondedBodies = new HashSet<Body>();
	protected Set<Vector2d> forces = new HashSet<Vector2d>();
	protected Set<Body> alreadyHit = new HashSet<Body>();
	protected double mass; // kg
	protected double charge; // coulombs
	protected double radius;
	protected Vector2d position; // m (pixels)
	protected Vector2d velocity; // m/s
	protected Shape shape;
	protected boolean visible = true, hidden;
	protected boolean temperatureColor;
	protected Color color = Color.black;
	protected World world;	

	public boolean canHitForce(Body c) {
		return distance(position, c.getPosition()) <= radius + c.radius;
	}

	public Set<Body> getBondedBodies() {
		return bondedBodies;
	}

	public boolean canHit(Body other) {
		return false;
	}

	public boolean intersects(Rectangle r) {
		AffineTransform af = AffineTransform.getTranslateInstance(
			position.getX()-radius, position.getY()-radius);
		Shape transformed = af.createTransformedShape(shape);
		return transformed.intersects(r);
	}

	public double getRadius() {
		return radius;
	}

	public void setGroup(long g) {
		group = g;
	}

	public long getGroup() {
		return group;
	}

	public void hit(Body other) { }

	public Body(Vector2d pos, Vector2d vel, double m, Shape s) {
		position = pos;
		velocity = vel;
		mass = m;
		shape = s;
	}

	public boolean intersectsRectangle(Rectangle2D r) {
		AffineTransform af = AffineTransform.getTranslateInstance(
			position.getX()-radius, position.getY()-radius);
		Shape transformed = af.createTransformedShape(shape);
		return transformed.intersects(r);
	}

	public void setVisible(boolean v) {
		visible = v;
	}

	// hide body; may be unhidden by world
	public void hide() {
		hidden = true;
	}

	// unhide body, may be rehidden by world
	public void unhide() {
		hidden = false;
	}

	public void setRadius(double r) {
		radius = r;
	}

	public void setTempColorEnabled(boolean t,World w) {
		temperatureColor = t;
		world = w;
	}

	public void step(double dt) { // seconds
		Vector2d accel = v();
		for (Vector2d f : forces)
			accel = add(accel, scale(f, 1/mass));
		for (Vector2d f : tmp)
			accel = add(accel, scale(f, 1/mass));
		for(BindingForce b: bonds)
			accel = add(accel, scale(b.getForce(), 1/mass));
		velocity = add(velocity, scale(accel, dt));
		position = add(position, scale(velocity, dt));
		tmp.clear();
		alreadyHit.clear();
	}

	public void addBond(BindingForce b, Body body) {
		bonds.add(b);
		bondedBodies.add(body);
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

	public Color getColor(double f) {
		double r=0,g=0,b=0;
		if (f < .2)
			b = 255*5*f;
		else if (f < .4) {
			b = 255;
			g=255*5*(f-.2);
		}
		else if(f <= 1) {
			r=255;
			g=255-(255*10*(f-.4)/6.0);	
		}
		else
			r = 255;
		return new Color((int)r,(int)g,(int)b);
	}

	public Color calculateColor() {
		Color c;
		double maxK = 100000;
		double k = .5*mass*Math.pow(velocity.length(),2);
		double fraction = k/maxK;
		c = getColor(fraction);
		return c;
	}

	public void paint(Graphics2D g2d) {
		if (!visible || hidden)
			return;
		
		if (temperatureColor)
			color = calculateColor();
		g2d.setColor(color);
		AffineTransform af = AffineTransform.getTranslateInstance(
			position.getX()-radius, position.getY()-radius);
		Shape transformed = af.createTransformedShape(shape);
		g2d.fill(transformed);
	}

	public void setColor(Color c) {
		color = c;
	}

	public Set<Vector2d> getForces() {
		return forces;
	}

	public double getKineticEnergy() {
		double k = mass*.5*Math.pow(velocity.length(),2);
		return k;
	}
}
