package nehsics.world;
import nehsics.bodies.*;
import nehsics.force.*;
import java.util.*;

public class FieldManager extends WorldAdapter {
	private Set<ForceField> fields = new HashSet<ForceField>();

	public void beginStep(World world, double dt) {
		for (Body body : world.bodies)
			for (ForceField field : fields)
				body.applyForce(field.getForce(body));
	}

	public void add(ForceField f) {
		fields.add(f);
	}
}
