package nehsics.test;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class CannonTester extends Tester {
	public final static String NAME = "Invisible Cannon Test";
	protected int count = 400; // XXX go by dt
	protected int i;

	public static void main(String[] args) {
		new Starter(NAME);
	}

	public CannonTester(Canvas c) {
		super(c);
	}

	protected void update(double dt) {
		if (i++ % 20 == 0)
			newCannonBall();
	}

	private void newCannonBall() {
		Circle a = new Circle(EARTH_RADIUS/40, 2);
		a.setPosition(v(0,-EARTH_RADIUS-EARTH_RADIUS/9));
		a.setVelocity(v(2000+2000*Math.random()+count*10,-1000));
		a.setVisible(false);
		world.addBody(a);
	}

	protected void setup() {
		display.setScale(2e-5);
		SPEED=500;

		// earth is down there (its the floor)
		Circle earth = new Circle(EARTH_RADIUS/10, EARTH_MASS);
		earth.setPosition(v(0,0));

		world.addBody(earth);
	}
}
