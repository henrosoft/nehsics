package nehsics.math;

public class Util {

	public static double EARTH_MASS = 5.9742e24;
	public static double EARTH_RADIUS = 6378000;
	public static double EARTH_ORBIT_V = 29.683e3;
	public static double EARTH_ORBIT_R = 1.49e11;
	public static double MERCURY_MASS = 3.3022e23;
	public static double MERCURY_RADIUS = 2439.7e3;
	public static double MERCURY_ORBIT_V = 47.87e3;
	public static double MERCURY_ORBIT_R = 57.909e9;
	public static double VENUS_MASS = 4.8685e24;
	public static double VENUS_RADIUS = 6051.8e3;
	public static double VENUS_ORBIT_V = 35.02e3;
	public static double VENUS_ORBIT_R = 1.08e11;
	public static double MARS_MASS = 6.4185e23;
	public static double MARS_RADIUS = 3396e3;
	public static double MARS_ORBIT_V = 24.077e3;
	public static double MARS_ORBIT_R = 2.27e11;
	public static double SUN_MASS = 1.9891e30;
	public static double SUN_RADIUS = 6.955e8;
	public static double SURFACE_G = 9.80665;
	public static double G = 6.67428e-11;

	public static Vector2d v(Number x, Number y) {
		return new Vector2d(x.doubleValue(), y.doubleValue());
	}

	public static Vector2d v(Number xy) {
		return new Vector2d(xy.doubleValue(), xy.doubleValue());	
	}

	public static Vector2d v() {
		return new Vector2d(0,0);
	}

	public static double distance(Vector2d a, Vector2d b) {
		return sub(a,b).length();
	}

	public static Vector2d normalise(Vector2d diff) {
		double scaler = Math.sqrt(Math.pow(diff.getX(),2) + Math.pow(diff.getY(),2));
		return scale(diff, 1 / scaler);
	}

	public static Vector2d add(Vector2d a, Vector2d b) {
		return new Vector2d(a.getX()+b.getX(),a.getY()+b.getY());
	}

	public static Vector2d sub(Vector2d a, Vector2d b) {
		return new Vector2d(a.getX()-b.getX(),a.getY()-b.getY());
	}

	public static Vector2d scale(Vector2d a, Vector2d b) {
		return v(a.getX()*b.getX(), a.getY()*b.getY());
	}

	public static Vector2d scale(Vector2d a, Number b) {
		return v(a.getX()*b.doubleValue(), a.getY()*b.doubleValue());
	}

	public static double sign(double d) {
		return d > 0 ?  1 : -1;
	}

	public static void sleep(double seconds) {
		try {
			Thread.sleep((int)(seconds*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
