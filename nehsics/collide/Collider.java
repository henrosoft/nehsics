package nehsics.collide;
import nehsics.bodies.*;
import java.util.*;

public class Collider {
	public void resolveCollisions(List<Body> bodies) {
		for (Body b : bodies)
			for (Body b2 : bodies)
				if (b != b2 && b.canHit(b2))
					b.hit(b2);
	}
}
