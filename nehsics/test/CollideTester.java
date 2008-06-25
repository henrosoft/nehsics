package nehsics.test;
import nehsics.ui.*;
import nehsics.world.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class CollideTester extends UserControlledScene {
	public final static String NAME = "Collision Tester";

	public static void main(String[] args) {
		new Starter("nehsics.test.CollideTester") ;
	}

	public CollideTester(Canvas c) {
		super(c);
	}

	protected void setup() {
		Stats s = new Stats();
		world.addListener(s);
		Collider collider = new Collider(s);
		world.addListener(collider);
		SPEED = .05;
		long group = 424242424;
		for (int i=0; i < 20; i++)
			for (int j=0; j < 20; j++) {
				Circle c = new Circle(4, 100);
				c.setTempColorEnabled(true, world);
				c.scaleTempColor(100);
				c.setPosition(v(j*4,i*4));
				c.setVelocity(v(-500,0));
				c.setGroup(group);
				world.addBody(c);
			}
		for (int i=3; i < 7; i++)
			for (int j=0; j < 10; j++) {
				Circle c = new Circle(4, 500);
				c.setTempColorEnabled(true, world);
				c.scaleTempColor(1000);
				c.setPosition(v(-100+j*4,i*2));
				c.setVelocity(v(1000,0));
				c.setGroup(group);
				world.addBody(c);
			}
	}
}
