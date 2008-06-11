package nehsics;
import java.awt.*;
import static nehsics.math.Util.*;

public class CollapseTest extends Tester {
	public final static String NAME = "Model Inaccuracies";

	public CollapseTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		display.setScale(5e-8);
		PRECISION = 1;
		for (int i=0; i < 10; i++) {
			for (int j=0; j < 10; j++) {
				Circle tmp = new Circle(SUN_RADIUS/2, SUN_MASS*1000000);
				tmp.setPosition(v(SUN_RADIUS*i-5*SUN_RADIUS,SUN_RADIUS*j-5*SUN_RADIUS));
				world.addBody(tmp);
			}
		}
	}
}
