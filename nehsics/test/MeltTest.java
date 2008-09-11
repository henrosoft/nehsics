package nehsics.test;
import nehsics.ui.*;
import nehsics.world.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class MeltTest extends UserControlledScene {
	public final static String NAME = "Melt Tester";

	public static void main(String[] args) {
		new Starter("nehsics.test.MeltTest") ;
	}

	public MeltTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		Stats s = new Stats();
		world.addListener(s);
		FieldManager f;
		world.addListener(f = new FieldManager());
		world.addListener(new Gravitation(f));
		Collider collider = new Collider(s);
		world.addListener(collider);
		display.setScale(3);
		SPEED = .05;
		long group = 424242424;
		for (int i=0; i < 15; i++)
			for (int j=0; j < 15; j++) {
				Circle c = new Circle(4, 100);
				c.setPosition(v(j*3-35,i*3-45));
				c.setGroup(group);
				world.addBody(c);
			}
		Circle x;
		world.addBody(x = new Circle(10,1000));
		x.setPosition(v(0,20));
		world.addBody(x = new Circle(EARTH_RADIUS, EARTH_MASS));
		x.setPosition(v(0, 30+EARTH_RADIUS));
	}
}
