package nehsics.world;
import nehsics.force.*;
import java.util.*;

public class Bonder extends WorldAdapter {
	private Set<SpringForce> forces = new HashSet<SpringForce>();

	public void beginStep(World world, double dt) {
		for (SpringForce s : forces)
			s.calculateForce();
	}

	public void addBond(SpringForce f) {
		forces.add(f);
	}
}
