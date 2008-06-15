package nehsics.world;
import nehsics.collide.*;
import nehsics.force.*;
import nehsics.bodies.*;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.Rectangle;
public class World {
	private List<Body> bodies = new LinkedList<Body>();
	private List<ForceField> fields = new LinkedList<ForceField>();
	private List<BindingForce> bonds = new LinkedList<BindingForce>();
	private boolean wall;
	private boolean gravity = true;
	private double averageMaxKinetic = 0;
	private double numCalculations = 0;
	private Collider collider = new QuadSpaceCollider();
	private boolean quad = true;
	public void setCollider(Collider c) {
		collider = c;
	}

	public void checkForCollisions() {
		if(!quad)
		{
			checkForCollisionsSquared();
			return;
		}
		if (wall)
			checkForWalls();
		collider.resolveCollisions(new QuadSpace(bodies));
	}
	public double averageKineticWithinBounds(double[] bounds)
	{
		double ave = 0;
		double count = 0;
		for(Body b: bodies)
		{
			if(b.intersects(new Rectangle((int)bounds[0],(int)bounds[1],(int)bounds[2],(int)bounds[3])))
			{
				count++;
				ave+=b.getKineticEnergy();
			}	
		}
		return (double)ave/(double)count;
	}
	public void setQuadSpaceEnabled(boolean q)
	{
		quad = q;
	}
	public void addBond(BindingForce b) {
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

	public double maxKineticEnergy() {
		double max = 0;
		for (Body b : bodies)
			if (b.getKineticEnergy() > max)
				max = b.getKineticEnergy();
		numCalculations++;
		averageMaxKinetic += max; 
		return averageMaxKinetic/numCalculations;
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
                if (p.getX() < b.getRadius()-250 && v.getX()<0)
                    v = v(Math.abs(v.getX()), v.getY());
                if (p.getY()< b.getRadius()-250 && v.getY()<0)
                    v = v(v.getX(), Math.abs(v.getY()));
                if (p.getX()>250-b.getRadius() && v.getX()>0)
                    v = v(-Math.abs(v.getX()), v.getY());
                if (p.getY()>250-b.getRadius() && v.getY()>0)
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
