package nehsics.collide;
import nehsics.world.*;
import nehsics.bodies.*;
import java.util.*;
import java.awt.Graphics2D;

public class QuadSpaceCollider extends BasicCollider {
	// some arbitrary constants; probably far from optimal
	private final static int TARGET_SPACE_SIZE = 10;
	private Set<QuadSpace> visuals = new HashSet<QuadSpace>();
	private Stats stats;

	public QuadSpaceCollider(Stats s) {
		stats = s;
	}

	public void resolveCollisions(List<Body> bodies) {
		visuals.clear();
		resolveCollisions(new QuadSpace(bodies));
	}

	public void paint(Graphics2D g2d) {
		for (QuadSpace q : visuals)
			q.paint(g2d);
	}

	private void resolveCollisions(QuadSpace space) {
		double avg_rad = stats.getAverageBodyRadius();
		if (space.size() <= TARGET_SPACE_SIZE || space.dim() < avg_rad*5) {
			visuals.add(space);
			super.resolveCollisions(space);
		} else for (QuadSpace qspace : space.divide())
			if (qspace.size() > 1)
				resolveCollisions(qspace);
	}
}
