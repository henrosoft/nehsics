package nehsics;
import static nehsics.math.Util.*;

public class CannonTester extends Tester {
	protected int count = 1; // XXX go by dt

	protected void update(double dt) {
		if (count++ % 100 == 0 && count < 800)
			newCannonBall();
	}

	private void newCannonBall() {
		Circle a = new Circle(EARTH_RADIUS/40, 2);
		a.setPosition(v(0,-EARTH_RADIUS-EARTH_RADIUS/9));
		a.setVelocity(v(4000+count*10,0));
		world.addBody(a);
	}

	protected void setup() {
		display.setFadeEnabled(true);
		display.setScale(1e-5);
		SPEED=1000;

		// earth is down there (its the floor)
		Circle earth = new Circle(EARTH_RADIUS, EARTH_MASS);
		earth.setPosition(v(0,0));

		world.addBody(earth);
	}

	public static void main(String[] args) {
		new CannonTester();
	}
}
