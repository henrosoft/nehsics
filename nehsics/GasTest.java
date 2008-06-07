package nehsics;
import nehsics.math.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class GasTest extends Tester {

	protected void setup() {
		PRECISION = 1;
		world.setWallsEnabled(true);
        int temp = 100;
		Circle c;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
				world.addBody(c = new Circle(10,10));	
				c.setPosition(v(50+50*i-250, 50+50*j-250));
				c.setVelocity(v(temp*(Math.random()-.5), temp*(Math.random()-.5)));
			}
	}

	public static void main(String[] args) {
		new GasTest();
	}
}
