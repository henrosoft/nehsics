package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.Graphics2D;
import java.util.*;

public class World {
	private List<Body> bodies = new LinkedList<Body>();
	private List<ForceField> fields = new LinkedList<ForceField>();
	private List<BindingForce> bonds = new LinkedList<BindingForce>();
	private boolean wall;
	private boolean gravity = true;
	private double averageMaxKinetic = 0;
	private double numCalculations = 0;
	private Collider collider = new QuadSpaceCollider();
	private boolean quadspace = true;
	public void addForce(Vector2d f) {
		for (Body b : bodies)
			if (b.getMass() < Double.POSITIVE_INFINITY)
				b.addForce(f);
	}
	public void setQuadSpaceEnabled(boolean q)
	{
		quadspace = q;
	}
	public void checkForCollisions() {
		if(!quadspace)
		{
			checkForCollisionsSquared();
			return;
		}
		if (wall)
			checkForWalls();
		collider.resolveCollisions(new QuadSpace(bodies));
	}
	public void addBond(BindingForce b)
	{
		bonds.add(b);
	}
    public void checkForCollisionsSquared() {
		if (wall)
			checkForWalls();
        for (Body b : bodies)
            for (Body b2 : bodies)
				if (b != b2 && b.canHit(b2))
					b.hit(b2);
    }
/*	public void checkForCollisions()
	{
		if (wall)
			checkForWalls();
		Vector2d minP = getMinP();
		Vector2d maxP = getMaxP();
		System.gc();
		QuadTree q = new QuadTree(new LinkedList<QuadTree>(), bodies, minP, maxP);
		q.checkForCollisions();
	}*/
	public Vector2d getMinP()
	{
		double x = 99999999;
		double y = 99999999;
		for(Body b: bodies)
		{
			if(b.getPosition().getX()<x)
				x = b.getPosition().getX();
			if(b.getPosition().getY()<y)
				y = b.getPosition().getY();
		}
		return v(x,y);
	}
	public Vector2d getMaxP()
	{
		double x = -99999999;
		double y = -99999999;
		for(Body b: bodies)
		{
			if(b.getPosition().getX()>x)
				x = b.getPosition().getX();
			if(b.getPosition().getY()>y)
				y = b.getPosition().getY();
		}
		return v(x,y);
	}
	public double maxKineticEnergy()
	{
		double max = 0;
		for(Body b: bodies)
			if(b.getKineticEnergy()>max)
				max = b.getKineticEnergy();
		numCalculations++;
		averageMaxKinetic+=max; 
		return averageMaxKinetic/(double)numCalculations;
	}
	public void setWallsEnabled(boolean w) {
		wall = w;
	}

	/**
	 * @param g Whether gravity fields should be automatically added for bodies.
	 * Note: only affects bodies added after gravity is set.
	 */
	public void setGravityEnabled(boolean g) {
		gravity = g;
	}

	// XXX Implement real walls.
    public void checkForWalls() {
        for (Body b : bodies) {
            if (b.getMass() != Double.POSITIVE_INFINITY) {
                Vector2d v = b.getVelocity();
                Vector2d p = b.getPosition();
                if(p.getX() < b.radius-250 && v.getX()<0)
                    v = v(Math.abs(v.getX()), v.getY());
                if(p.getY()< b.radius-250 && v.getY()<0)
                    v = v(v.getX(), Math.abs(v.getY()));
                if(p.getX()>250-b.radius && v.getX()>0)
                    v = v(-Math.abs(v.getX()), v.getY());
                if(p.getY()>250-b.radius && v.getY()>0)
                    v = v(v.getX(), -Math.abs(v.getY()));
				b.setVelocity(v);
            }
        }
    }

	public void addBody(Body b) {
		bodies.add(b);
		if (gravity)
			fields.add(new GravityField(b));
	}

	public void addField(ForceField field) {
		fields.add(field);
	}

	public void step(double dt) {
		for(BindingForce b : bonds)
			b.calculateForce();
		checkForCollisions();
		for (Body body : bodies)
			for (ForceField field : fields)
				body.applyForce(field.getForce(body));
		for (Body body : bodies)
			body.step(dt);
	}

	public void paint(Graphics2D g2d) {
		for (Body body : bodies)
			body.paint(g2d);
	}
	
	public int nextBodyIndex(int current) {
		return (current + 1) % bodies.size();
	}
	
	public int prevBodyIndex(int current) {
		return (current - 1 + bodies.size()) % bodies.size();
	}
	
	public Body getBodyFromIndex(int current) {
		return bodies.get(current);
	}
}
