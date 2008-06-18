package nehsics.world;
import nehsics.collide.ApproxCollider;

public class BadCollider extends WorldAdapter {
	private ApproxCollider collider;

	// over 30 is wasteful
	// under 10 is bad (FIXME)
	public BadCollider(int precision) {
		collider = new ApproxCollider(precision);
	}

	public void beginStep(World world, double dt) {
		collider.resolveCollisions(world.bodies);
	}
}
