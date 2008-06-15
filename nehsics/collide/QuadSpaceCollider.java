package nehsics.collide;
import nehsics.bodies.*;
import java.util.*;

public class QuadSpaceCollider extends Collider {
	// some arbitrary constants; probably far from optimal
	private final static int TARGET_SPACE_SIZE = 5;
	private double avg_rad;

	public void resolveCollisions(List<Body> bodies) {
		double i = 0, sum = 0;
		for (Body body : bodies) {
			i++;
			sum += body.getRadius();
		}
		avg_rad = sum / i;
		resolveCollisions(new QuadSpace(bodies));
	}

	private void resolveCollisions(QuadSpace space) {
		if (space.size() <= TARGET_SPACE_SIZE || space.dim() < avg_rad*5)
			super.resolveCollisions(space);
		else
			for (QuadSpace qspace : space.divide())
				if (qspace.size() > 1)
					resolveCollisions(qspace);
	}
}
