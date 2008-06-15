package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.awt.geom.*;
public abstract class Body {
	// tmp forces will be cleared after each step!
	protected Set<Vector2d> tmp = new HashSet<Vector2d>();
	protected Set<BindingForce> bonds = new HashSet<BindingForce>();
	protected Set<Vector2d> forces = new HashSet<Vector2d>();
	protected Set<Body> alreadyHit = new HashSet<Body>();
	protected double mass; // kg
	protected double charge; // coulombs
	protected double radius;
	protected Vector2d position; // m (pixels)
	protected Vector2d velocity; // m/s
	protected Shape shape;
	protected boolean visible = true;
	protected boolean temperatureColor;
	protected Color color = Color.black;
	protected World world;	
	public boolean canHit(Body other) {
		return false;
	}

	public double getRadius() {
		return radius;
	}

	public void hit(Body other) { }

	public Body(Vector2d pos, Vector2d vel, double m, Shape s) {
		position = pos;
		velocity = vel;
		mass = m;
		shape = s;
	}
	public boolean intersectsRectangle(Rectangle2D r)
	{
		AffineTransform af = AffineTransform.getTranslateInstance(
			position.getX()-radius, position.getY()-radius);
		Shape transformed = af.createTransformedShape(shape);
		return transformed.intersects(r);
	}
	public void setVisible(boolean v) {
		visible = v;
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
	public void addBond(BindingForce b)
	{
		bonds.add(b);
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
/*		f = 1.0-f;
		double r = (Math.cos(f*Math.PI*2)+1)*(255.0/2.0);
		double g = (-Math.cos((f*Math.PI*2)+Math.PI/3.0)+1)*(255.0/2.0);
		double b = (-Math.cos((f*Math.PI*2)-Math.PI/3.0)+1)*(255.0/2.0);*/
		double r=0,g=0,b=0;
		if(f<.2)
			b = 255*5*f;
		else if(f<.4)
		{
			b = 255;
			g=255*5*(f-.2);
		}
		else if(f<.5)
		{
			g=255;
			b=255-(255*10*(f-.4));
		}
		else if(f<.6)
		{
			g=255;
			r=(255*10*(f-.5));
		}
		else if(f<=1)
		{
			r=255;
			g=255-(255*10*(f-.6)/4.0);	
		}
		else
			r = 255;
		return new Color((int)r,(int)g,(int)b);
	}

	public Color calculateColor() {
		Color c;
//		double maxK = world.maxKineticEnergy();
		double maxK = 100000;
		double k = .5*mass*Math.pow(velocity.length(),2);
		double fraction = k/maxK;
		c = getColor(fraction);
/*		if (fraction>1)
			c = new Color(255,0,0);
		else if(fraction<1.0/2.0)
			c = new Color((int)(255*2*fraction),
				(int)(255*2*fraction),(int)(255-(255*2*fraction)));
		else
			c = new Color(255,(int)(255-(255*2*(fraction-.5))),0);*/
		return c;
	}

	public void paint(Graphics2D g2d) {
		if (!visible)
			return;
		if (temperatureColor)
			color = calculateColor();
		g2d.setColor(color);
		AffineTransform af = AffineTransform.getTranslateInstance(
			position.getX()-radius, position.getY()-radius);
		Shape transformed = af.createTransformedShape(shape);
		g2d.fill(transformed);
	}
	public void setColor(Color c)
	{
		color = c;
	}
	public Set<Vector2d> getForces() {
		return forces;
	}
	public double getKineticEnergy()
	{
		double k = mass*.5*Math.pow(velocity.length(),2);
		return k;
	}
}
