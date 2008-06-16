package nehsics.world;
import nehsics.math.*;
import static nehsics.math.Util.*;
import nehsics.bodies.*;

public class Walls extends WorldAdapter {
	private int wall;
	private double damping = 1;

	public Walls(int size) {
		wall = size;
	}

	public Walls(int size, double d) {
		wall = size;
		damping = d;
	}

	public void beginStep(World world, double dt) {
        for (Body b : world.bodies) {
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
        }
	}
}
