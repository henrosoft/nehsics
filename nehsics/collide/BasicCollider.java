package nehsics.collide;
import nehsics.bodies.*;
import java.util.*;
import java.awt.Graphics2D;

public class BasicCollider {

	public void resolveCollisions(List<Body> bodies) {
		Body a, b;
		for (int i=0; i < bodies.size(); i++) {
			a = bodies.get(i);
			for (int j=i+1; j < bodies.size(); j++) {
				b = bodies.get(j);
				if (a.canHit(b))
					a.hit(b);
			}
		}
	}

	public void paint(Graphics2D g2d) {}
}
