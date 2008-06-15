package nehsics.collide;
import nehsics.bodies.*;
import java.util.*;
import java.awt.Graphics2D;

public class QuadSpaceCollider extends Collider {
	// some arbitrary constants; probably far from optimal
	private final static int TARGET_SPACE_SIZE = 5;
	private Set<QuadSpace> visuals = new HashSet<QuadSpace>();
	private double avg_rad;

	public void resolveCollisions(List<Body> bodies) {
		visuals.clear();
		double i = 0, sum = 0;
		for (Body body : bodies) {
			i++;
			sum += body.getRadius();
		}
		avg_rad = sum / i;
		resolveCollisions(new QuadSpace(bodies));
	}

	public void paint(Graphics2D g2d) {
		for (QuadSpace q : visuals)
			q.paint(g2d);
	}

	private void resolveCollisions(QuadSpace space) {
		if (space.size() <= TARGET_SPACE_SIZE || space.dim() < avg_rad*5) {
			visuals.add(space);
			super.resolveCollisions(space);
		} else for (QuadSpace qspace : space.divide())
			resolveCollisions(qspace);
	}
}
