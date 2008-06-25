package nehsics.test;
import nehsics.world.*;
import nehsics.force.*;
import nehsics.ui.*;
import nehsics.bodies.*;
import static nehsics.math.Util.*;
import java.awt.*;

public class DropTest extends UserControlledScene {
	public final static String NAME = "Falling Spheres";

	public static void main(String[] args) {
		new Starter("nehsics.test.DropTest");
	}

	public DropTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		display.enableAA();
		FieldManager f = new FieldManager();
		f.add(new ResistiveForce(1));
		Stats s = new Stats();
		world.addListener(s);
		world.addListener(f);
		world.addListener(new Gravitation(f));
		world.addListener(new Collider(s));
		SPEED = 5;
		PRECISION = 5;

		// large circles... (low density here)
		Circle a = new Circle(45,10);
		a.scaleTempColor(.5);
		a.setTempColorEnabled(true, world);
		a.setPosition(v(-110,-150));

		// fall as fast as small circles (high density here)	
		Circle b = new Circle(15,500);
		b.setTempColorEnabled(true, world);
		b.scaleTempColor(50);
		b.setPosition(v(-210,-150));
		b.setVelocity(v(1,0));

		// normal force opposes gravity
		Circle stationary = new Circle(40,10);
		stationary.setTempColorEnabled(true, world);
		stationary.setPosition(v(0,50));
		stationary.addForce(v(0,-SURFACE_G*stationary.getMass()));
		
		// earth is down there (its the floor)
		Circle earth = new Circle(EARTH_RADIUS, EARTH_MASS);
		earth.setPosition(v(0,EARTH_RADIUS+150));

		world.addBody(a);
		world.addBody(b);
		world.addBody(stationary);
		world.addBody(earth);
	}
}
