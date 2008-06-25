package nehsics.world;
import java.awt.*;
import nehsics.collide.ApproxCollider;

public class BadCollider extends WorldAdapter {
	private ApproxCollider collider;
	private boolean visible;

	// over 30 is wasteful
	// under 10 is bad (FIXME)
	public BadCollider(int precision) {
		collider = new ApproxCollider(precision);
	}

	public void beginStep(World world, double dt) {
		collider.resolveCollisions(world.bodies);
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public void paint(Graphics2D g2d) {
		if (visible)
			collider.paint(g2d);
	}
}
