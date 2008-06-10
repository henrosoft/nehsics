package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.Graphics2D;
import java.util.*;

public class World {
	private List<Body> bodies = new LinkedList<Body>();
	private List<ForceField> fields = new LinkedList<ForceField>();
	private boolean wall;
	private boolean gravity = true;

	public void addForce(Vector2d f) {
		for (Body b : bodies)
			if (b.getMass() < Double.POSITIVE_INFINITY)
				b.addForce(f);
	}

    public void checkForCollisions() {
		if (wall)
			checkForWalls();
        for (Body b : bodies)
            for (Body b2 : bodies)
                if (b2 != b) {
                    if (b instanceof Circle
							&& b2 instanceof Circle
							&& ((Circle)b).intersects((Circle)b2))
                        ((Circle)b).hit(b2);
                }
    }
	public double maxKinectic()
	{
		double max = 0;
		return max;
	}
	public void setWallsEnabled(boolean w) {
		wall = w;
	}

	public void setGravityEnabled(boolean w) {
		gravity = w;
	}

	// XXX
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
