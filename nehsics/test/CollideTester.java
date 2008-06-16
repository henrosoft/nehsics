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
		collider.setVisible(true);
		world.addListener(collider);
		PRECISION = 1;
		SPEED = .1;
		for (int i=0; i < 20; i++)
			for (int j=0; j < 20; j++) {
				Circle c = new Circle(4, 4);
				c.setPosition(v(j*8,i*8));
				world.addBody(c);
			}
		Circle x = new Circle(30, 5000);
		x.setPosition(v(-200, 80));
		x.setVelocity(v(500,0));
		world.addBody(x);
	}
}
