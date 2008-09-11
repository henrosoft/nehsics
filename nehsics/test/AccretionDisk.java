package nehsics.test;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class AccretionDisk extends UserControlledScene {
	public final static String NAME = "Accretion Disk";

	public static void main(String[] args) {
		new Starter("nehsics.test.AccretionDisk");
	}

	public AccretionDisk(Canvas c) {
		super(c);
	}

	protected void setup() {
		for (int i=0; i < 30; i++)
			for (int j=0; j < 30; j++) {
				Circle a = new Circle(EARTH_RADIUS/80, 2);
				a.scaleTempColor(1e4);
				a.setTempColorEnabled(true, world);
				a.setPosition(v(j*EARTH_RADIUS/120,
					-.3*EARTH_RADIUS-i*EARTH_RADIUS/120));
				a.setVelocity(v(12000, 0));
				a.setGroup(1234); // model dust clouds inelastically
				world.addBody(a); //+later implement some middle ground?
			}
		FieldManager f = new FieldManager();
		world.addListener(f);
		world.addListener(new Gravitation(f));
		world.addListener(new BadCollider(10));
		display.setScale(3e-5);
		SPEED = 200;
		Circle center = new Circle(0, EARTH_MASS);
		center.setPosition(v(0,0));
		world.addBody(center);
	}
}
