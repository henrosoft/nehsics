package nehsics.test;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class CollideTester extends Tester {
	public final static String NAME = "Collision Tester";

	public static void main(String[] args) {
		new Starter(NAME);
	}

	public CollideTester(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		SPEED = .2;
		world.setGravityEnabled(false);
		for (int i=0; i < 20; i++) {
			for (int j=0; j < 20; j++) {
				Circle c = new Circle(5, 5);
				c.setPosition(v(j*10,i*10));
				world.addBody(c);
			}
		}
		Circle x = new Circle(50, 200);
		x.setPosition(v(-300, 35));
		x.setVelocity(v(750,1));
		world.addBody(x);
	}
}
