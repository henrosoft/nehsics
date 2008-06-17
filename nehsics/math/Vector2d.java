package nehsics.math;

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

	public boolean equals(Object other) {
		Vector2d b = (Vector2d)other;
		return b.x == x && b.y == y;
	}
}
