package nehsics.test;
import nehsics.ui.*;
import nehsics.world.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class CollideTester extends Tester {
	public final static String NAME = "Collision Tester";

	public static void main(String[] args) {
		new Starter(NAME) ;
	}

	public CollideTester(Canvas c) {
		super(c);
	}

	protected void setup() {
		Stats s = new Stats();
		world.addListener(s);
		Collider collider = new Collider(s);
		world.addListener(collider);
		SPEED = .1;
		long group = 424242424;
		for (int i=0; i < 50; i++)
			for (int j=0; j < 5; j++) {
				Circle c = new Circle(4, 100);
				c.setPosition(v(j*4,i*4-100));
				c.setGroup(group);
				world.addBody(c);
			}
		Circle x = new Circle(2, 50000);
		x.setPosition(v(-900, 50));
		x.setVelocity(v(2500,0));
		world.addBody(x);
		x = new Circle(20, 50);
		x.setPosition(v(-200, -50));
		x.setVelocity(v(500,0));
		world.addBody(x);
	}
}
