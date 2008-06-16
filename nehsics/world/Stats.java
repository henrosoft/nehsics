package nehsics.world;
import nehsics.bodies.*;
import nehsics.math.*;
import java.awt.*;

public class Stats extends WorldAdapter {
	private Average avgRadius = new Average();
	private World world;

	public void newBody(World world, Body body) {
		avgRadius.add(body.getRadius());	
		this.world = world; // XXX
	}

	public double getAverageBodyRadius() {
		return avgRadius.getAvg();
	}

	public double averageKineticWithinBounds(double[] bounds) {
		if (world == null)
			return 0;
		Average a = new Average();
		Rectangle rect = new Rectangle (
			(int)bounds[0],(int)bounds[1],
			(int)bounds[2],(int)bounds[3]
		);
		for (Body b : world.bodies)
			if (b.intersects(rect))
				a.add(b.getKineticEnergy());
		return a.getAvg();
	}
}
