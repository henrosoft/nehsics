package nehsics.test;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class OrbitTest extends UserControlledScene {
	public final static String NAME = "Inner Solar System";

	public static void main(String[] args) {
		new Starter("nehsics.test.OrbitTest");
	}

	public OrbitTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 5;
		display.enableAA();
		FieldManager f = new FieldManager();
		world.addListener(f);
		world.addListener(new Gravitation(f));

		display.setScale(1e-9);

		SPEED = 365*24*60; // 1 year / min

		Circle sun = new Circle(SUN_RADIUS*10, SUN_MASS);
		sun.setColor(Color.YELLOW);

		Circle earth = new Circle(EARTH_RADIUS*300, EARTH_MASS);
		earth.setColor(Color.BLUE);
		earth.setPosition(v(-EARTH_ORBIT_R,0));
		earth.setVelocity(v(0,EARTH_ORBIT_V));

		Circle mercury = new Circle(MERCURY_RADIUS*300, MERCURY_MASS);
		mercury.setColor(Color.darkGray);
		mercury.setPosition(v(-MERCURY_ORBIT_R,0));
		mercury.setVelocity(v(0,MERCURY_ORBIT_V));

		Circle venus = new Circle(VENUS_RADIUS*300, VENUS_MASS);
		venus.setColor(Color.ORANGE);
		venus.setPosition(v(-VENUS_ORBIT_R,0));
		venus.setVelocity(v(0,VENUS_ORBIT_V));

		Circle mars = new Circle(MARS_RADIUS*300, MARS_MASS);
		mars.setColor(Color.RED);
		mars.setPosition(v(-MARS_ORBIT_R,0));
		mars.setVelocity(v(0,MARS_ORBIT_V));

		world.addBody(sun);
		world.addBody(earth);
		world.addBody(mercury);
		world.addBody(venus);
		world.addBody(mars);
	}
}
