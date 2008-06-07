package nehsics;
import static nehsics.math.Util.*;

public class OrbitTest extends Tester {

	protected void setup() {
		display.setScale(1e-9);
		SPEED = 365*24*60; // 1 year / min

		Circle sun = new Circle(SUN_RADIUS*10, SUN_MASS);

		Circle earth = new Circle(EARTH_RADIUS*300, EARTH_MASS);
		earth.setPosition(v(-EARTH_ORBIT_R,0));
		earth.setVelocity(v(0,EARTH_ORBIT_V));

		Circle mercury = new Circle(MERCURY_RADIUS*300, MERCURY_MASS);
		mercury.setPosition(v(-MERCURY_ORBIT_R,0));
		mercury.setVelocity(v(0,MERCURY_ORBIT_V));

		Circle venus = new Circle(VENUS_RADIUS*300, VENUS_MASS);
		venus.setPosition(v(-VENUS_ORBIT_R,0));
		venus.setVelocity(v(0,VENUS_ORBIT_V));

		Circle mars = new Circle(MARS_RADIUS*300, MARS_MASS);
		mars.setPosition(v(-MARS_ORBIT_R,0));
		mars.setVelocity(v(0,MARS_ORBIT_V));

		world.addBody(sun);
		world.addBody(earth);
		world.addBody(mercury);
		world.addBody(venus);
		world.addBody(mars);
	}

	public static void main(String[] args) {
		new OrbitTest();
	}
}
