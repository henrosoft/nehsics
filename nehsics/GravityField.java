package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;

public class GravityField implements ForceField {
	private Body origin;

	public GravityField(Body body) {
		origin = body;
	}

	public Vector2d getForce(Body body) {
		Vector2d grav = v();
		if (body != origin) {
			double mag = G*body.getMass()*origin.getMass()
				/ Math.pow(distance(body.getPosition(), origin.getPosition()),2);
			if (mag == Double.POSITIVE_INFINITY)
				throw new IllegalArgumentException("Bodies too close!");
			Vector2d direction = normalise(body.getPosition(), origin.getPosition());
			grav = add(grav, scale(direction, mag));
		}
		return grav;
	}
}
