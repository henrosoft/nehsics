package nehsics.force;
import nehsics.bodies.*;
import nehsics.math.*;

public interface ForceField {
	public Vector2d getForce(Body body);
}
