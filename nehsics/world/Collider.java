package nehsics.world;
import nehsics.bodies.*;

import java.awt.Graphics2D;

import java.util.List;

import nehsics.collide.QuadSpaceCollider;

public class Collider extends WorldAdapter {
	private QuadSpaceCollider collider;
	private boolean visible;

	public Collider(Stats s) {
		collider = new QuadSpaceCollider(s);
	}

	public void beginStep(List<Body> bodies, double dt) {
		collider.resolveCollisions(bodies);
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public void paint(Graphics2D g2d) {
		if (visible)
			collider.paint(g2d);
	}
}
