package nehsics;
import java.awt.*;
import static nehsics.math.Util.*;

public class ChainReactionTester extends Tester {

	public ChainReactionTester(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		SPEED = .2;
		world.setWallsEnabled(true);	
		world.setGravityEnabled(false);
		Circle x = new Circle(31, 50);
		x.setPosition(v(-300, 25));
		x.setVelocity(v(350,1));
		
		Circle a = new Circle (15, 25);
		a.setPosition(v(-200, 40));
		Circle b = new Circle (15, 25);
		b.setPosition(v(-200, 10));

		world.addBody(x);
		world.addBody(a);
		world.addBody(b);
	}
}
