package nehsics;
import java.awt.*;
import static nehsics.math.Util.*;

public class SquareTester extends Tester {

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
				Circle c = new Circle(10, 10);
				c.setPosition(v(j*20,i*20));
				world.addBody(c);
//				c.setVisible(false);
//				c.setTemperatureColor(true);
			}
		}
	/*	for(int i = 0; i<5; i++)
		{
			Circle c = new Circle(10,10);
			c.setPosition(v(0,i*17));
			world.addBody(c);
		}*/
		Circle x = new Circle(50, 200);
		x.setPosition(v(-300, 35));
		x.setVelocity(v(750,1));
		world.addBody(x);
	}

	public void postWorld() {
		Graphics2D g2d = display.getGraphics();
		g2d.drawString("WARNING INVISIBLE CIRCLES", 30, 30);
	}
}