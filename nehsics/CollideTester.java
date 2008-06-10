package nehsics;
import java.awt.*;
import static nehsics.math.Util.*;

public class CollideTester extends Tester {
	public final static String NAME = "Collision Tester";

	public CollideTester(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		SPEED = .2;
		world.setGravityEnabled(false);
		world.setWallsEnabled(true);
		for (int i=0; i < 10; i++) {
			for (int j=0; j < 10; j++) {
				Circle c = new Circle(9, 9);
				c.setPosition(v(j*20,i*20));
				world.addBody(c);
			}
		}
		Circle x = new Circle(50, 20);
		x.setPosition(v(-300, 35));
		x.setVelocity(v(750,1));
		world.addBody(x);
	}
}
