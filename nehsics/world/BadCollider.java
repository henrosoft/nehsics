package nehsics.world;
import nehsics.bodies.*;

import java.awt.*;

import java.util.List;

import nehsics.collide.ApproxCollider;

public class BadCollider extends WorldAdapter {
	private ApproxCollider collider;
	private boolean visible;

	// over 30 is wasteful
	// under 10 is bad (FIXME)
	public BadCollider(int precision) {
		collider = new ApproxCollider(precision);
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
