package nehsics;
import java.awt.*;
import static nehsics.math.Util.*;
import java.awt.*;
public class TestOneD extends Tester {
	public final static String NAME = "One Double";

	public TestOneD(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		world.setGravityEnabled(false);
		world.setWallsEnabled(true);
		Circle c;
		world.addBody(c = new Circle(10,10));	
		c.setPosition(v(-100,0));
		c.setVelocity(v(30,0));
		world.addBody(c = new Circle(10,10));	
		c.setColor(Color.green);
		c.setPosition(v(100,0));
		c.setVelocity(v(-30,0));
	}
}
