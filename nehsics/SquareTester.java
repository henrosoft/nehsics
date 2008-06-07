package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;

public class SquareTester extends Tester {

	protected void setup() {
		PRECISION = 1;
		SPEED = .2;
		world.setWallsEnabled(true);	
		world.setGravityEnabled(false);
		for (int i=0; i < 10; i++) {
			for (int j=0; j < 10; j++) {
				Circle c = new Circle(2, 2);
				c.setPosition(v(j*10,i*10));
				world.addBody(c);
			}
		}
		Circle x = new Circle(10, 100);
		x.setPosition(v(-300, 45));
		x.setVelocity(v(350,1));
		world.addBody(x);
	}

	public static void main(String[] args) {
		new SquareTester();
	}
}
