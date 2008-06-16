package nehsics.world;
import nehsics.collide.QuadSpaceCollider;
import java.awt.Graphics2D;

public class Collider extends WorldAdapter {
	private QuadSpaceCollider collider;
	private boolean visible;

	public Collider(Stats s) {
		collider = new QuadSpaceCollider(s);
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
