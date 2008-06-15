package nehsics.collide;
import nehsics.bodies.*;
import java.util.*;

public class QuadSpaceCollider extends Collider {
	private final static int TARGET_SPACE_SIZE = 10;
	private final static int MAX_DEPTH = 10;
	private final static boolean verbose = false;

	public void resolveCollisions(List<Body> bodies) {
		if (verbose) System.out.println();
		resolveCollisions(new QuadSpace(bodies), 1);
	}

	private void resolveCollisions(QuadSpace space, int i) {
		if (space.size() <= TARGET_SPACE_SIZE || i > MAX_DEPTH) {
			super.resolveCollisions(space);
		} else {
			if (verbose) System.out.print(space.size() + " ");
			for (QuadSpace qspace : space.divide())
				resolveCollisions(qspace, i + 1);
		}
	}
}
