package nehsics.test;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class BrownianMotionTester extends Tester {
	public final static String NAME = "Brownian Motion";

	public static void main(String[] args) {
		new Starter(NAME);
	}

	public BrownianMotionTester(Canvas c) {
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
				c.setVisible(false);
			}
		}

		Circle x = new Circle(50, 200);
		x.setPosition(v(-300, 35));
		x.setVelocity(v(750,1));
		world.addBody(x);
	}

	public void postWorld() {
		super.postWorld();
		Graphics2D g2d = display.getGraphics();
		g2d.setColor(Color.BLACK);
		g2d.drawString("INVISIBLE PARTICLES HERE", 30, 30);
	}
}
