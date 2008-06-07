package nehsics.math;
import java.awt.geom.*;

public final class Vector2d {
	private final double x, y;

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public double length() {
		return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
