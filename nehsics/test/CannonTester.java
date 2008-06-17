package nehsics.test;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class CannonTester extends Tester {
	public final static String NAME = "Particle Stress Test";
	protected long lastTime;

	public static void main(String[] args) {
		new Starter("nehsics.test.CannonTester");
	}

	public CannonTester(Canvas c) {
		super(c);
	}

	protected void update(double dt) {
		if (System.currentTimeMillis() - lastTime > 500) {
			newCannonBall();
			lastTime = System.currentTimeMillis();
		}
	}

	private void newCannonBall() {
		Circle a = new Circle(EARTH_RADIUS/100, 2);
		a.setPosition(v(0,-EARTH_RADIUS-EARTH_RADIUS/9));
		a.setVelocity(v(4000+2000*Math.random(),-1000));
		world.addBody(a);
	}

	protected void setup() {
		FieldManager f = new FieldManager();
		Stats s = new Stats();
		world.addListener(s);
		world.addListener(f);
		world.addListener(new Gravitation(f));
		Collider col = new Collider(s);
		world.addListener(col);
		display.setScale(2e-5);
		col.setVisible(true);
		SPEED=500;
		Circle center = new Circle(EARTH_RADIUS/10, EARTH_MASS);
		center.setPosition(v(0,0));
		world.addBody(center);
	}
}
