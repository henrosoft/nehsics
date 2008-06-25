package nehsics.world;

import java.util.List;

import nehsics.bodies.*;

import nehsics.force.*;

public class Gravitation extends WorldAdapter {
	private FieldManager fields;

	public Gravitation(FieldManager f) {
		fields = f;
	}

	public void newBody(List<Body> bodies, Body origin) {
		fields.add(new GravityField(origin));
	}
}
