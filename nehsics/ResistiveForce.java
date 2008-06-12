package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;

public class ResistiveForce implements ForceField {
	private double b;

	public ResistiveForce(double magnitude) {
		b = magnitude;
	}

	public Vector2d getForce(Body body) {
		return scale(body.getVelocity(), -b);
	}
}
