package nehsics.world;
import nehsics.math.*;
import static nehsics.math.Util.*;
import nehsics.bodies.*;
import java.util.*;

public class Walls extends WorldAdapter {
	private int wall;
	private double damping = 1;
	private double internalEnergy = 0;
	private Set<Body> withinWalls = new HashSet<Body>();

	public Walls(int size) {
		wall = size;
	}

	public Walls(int size, double d) {
		wall = size;
		damping = d;
	}

	public double getInternalEnergy() {
		return internalEnergy;
	}

	public void rescale(int size) {
		wall = size;
		withinWalls.clear();
	}

	public void beginStep(World world, double dt) {
        for (Body b : world.bodies) {
			double k = b.getKineticEnergy();
			Vector2d v = b.getVelocity();
			Vector2d p = b.getPosition();
			double x = p.getX(), y = p.getY(), r = b.getRadius();
			if (x + r > wall)
				v = scale(v, v(v.getX() > 0 ? -damping : 1, 1));
			else if (x - r < -wall)
				v = scale(v, v(v.getX() < 0 ? -damping : 1, 1));
			if (y + r > wall)
				v = scale(v, v(1, v.getY() < 0 ? 1 : -damping));
			else if (y - r < -wall)
				v = scale(v, v(1, v.getY() > 0 ? 1 : -damping));
			b.setVelocity(v);
			internalEnergy += k-b.getKineticEnergy();
        }
	}

	public void endStep(World world, double dt) {
		for (Body b : world.bodies) {
			Vector2d p = b.getPosition();
			double x = p.getX(), y = p.getY(), r = b.getRadius();
			if (x - r > wall || x + r < -wall || y - r > wall || y + r < -wall) {
				if (withinWalls.contains(b))
					b.hide();
			} else {
				withinWalls.add(b);
				b.unhide();
			}
		}
	}
}
