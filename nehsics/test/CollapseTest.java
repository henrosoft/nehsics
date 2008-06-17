package nehsics.test;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class CollapseTest extends Tester {
	public final static String NAME = "Minimum PE Principle";

	public static void main(String[] args) {
		new Starter(NAME);
	}

	public CollapseTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		FieldManager f = new FieldManager();
		world.addListener(f);
		world.addListener(new Gravitation(f));
		display.setScale(3e-8);
		SPEED = .25;
		for (int i=0; i < 11; i++)
			for (int j=0; j < 11; j++) {
				Circle tmp = new Circle(SUN_RADIUS/2, SUN_MASS*1e6);
				tmp.setPosition(v(SUN_RADIUS*i-5*SUN_RADIUS,
					SUN_RADIUS*j-5*SUN_RADIUS));
				world.addBody(tmp);
			}
	}
}
