package nehsics;
import java.awt.*;
import static nehsics.math.Util.*;

public class GasTest extends Tester {
	public final static String NAME = "Ideal Gas Model";

	public GasTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		world.setWallsEnabled(true);
		world.setGravityEnabled(false);
        int temp = 100;
        int temp = 1000;
		Circle c;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
				world.addBody(c = new Circle(5,10));	
				c.setTempColorEnabled(true);
				c.setPosition(v(50+50*i-250, 50+50*j-250));
        for (int i = 0; i < 13; i++)
            for (int j = 0; j < 13; j++) {
				world.addBody(c = new Circle(5,5));	
				c.setPosition(v(50+25*i-250, 50+25*j-250));
				c.setVelocity(v(temp*(Math.random()-.5), temp*(Math.random()-.5)));
				c.setTemperatureColor(true);
			}
	}
}
