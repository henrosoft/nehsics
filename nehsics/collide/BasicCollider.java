package nehsics.collide;
import nehsics.bodies.*;
import java.util.*;
import java.awt.Graphics2D;

public class BasicCollider {

	public void resolveCollisions(List<Body> bodies) {
		for (Body b : bodies)
			for (Body b2 : bodies)
				if (b != b2 && b.canHit(b2))
					b.hit(b2);
	}

	public void paint(Graphics2D g2d) {}
}
