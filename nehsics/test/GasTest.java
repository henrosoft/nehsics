package nehsics.test;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class GasTest extends Tester {
	public final static String NAME = "Ideal Gas Model";

	public static void main(String[] args) {
		new Starter(NAME);
	}

	public GasTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		world.setWallsEnabled(true);
		world.setGravityEnabled(false);
        int temp = 100;
		Circle c;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
				world.addBody(c = new Circle(10,10));	
				c.setPosition(v(50+25*i-150, 50+25*j-150));
				c.setVelocity(v(temp*(Math.random()-.5), temp*(Math.random()-.5)));
				c.setTempColorEnabled(true,world);
			}
	}
}
