package nehsics.world;
import nehsics.bodies.*;
import nehsics.force.*;

public class Gravitation extends WorldAdapter {
	private FieldManager fields;

	public Gravitation(FieldManager f) {
		fields = f;
	}

	public void newBody(World world, Body origin) {
		fields.add(new GravityField(origin));
	}
}
