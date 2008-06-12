package nehsics;
import java.awt.*;
import static nehsics.math.Util.*;

public class SquareTester extends Tester {
	public final static String NAME = "Collision";

	public SquareTester(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		SPEED = .2;
		world.setWallsEnabled(true);	
		world.setGravityEnabled(false);
		for (int i=0; i < 10; i++) {
			for (int j=0; j < 10; j++) {
				Circle c = new Circle(5, 5);
				c.setPosition(v(j*10,i*10));
				world.addBody(c);
				c.setVisible(false);
//				c.setTemperatureColor(true);
				c.setVisible(true);
			}
		}
	/*	for(int i = 0; i<5; i++)
		{
			Circle c = new Circle(10,10);
			c.setPosition(v(0,i*17));
			world.addBody(c);
		}*/
		Circle x = new Circle(10, 10);
		x.setPosition(v(-300, 20));
		x.setVelocity(v(500, 1));
		world.addBody(x);
	}

	/* public void postWorld() {
		Graphics2D g2d = display.getGraphics();
		g2d.setColor(Color.BLACK);
		g2d.drawString("INVISIBLE PARTICLES HERE", 30, 30);
	} */
}
