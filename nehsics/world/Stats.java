package nehsics.world;

import java.awt.*;

import java.util.List;

import nehsics.bodies.*;

import nehsics.math.*;

public class Stats extends WorldAdapter {
	private Average avgRadius = new Average();
	private List<Body> bodies;

	public void newBody(List<Body> bodies, Body body) {
		avgRadius.add(body.getRadius());	
		this.bodies = bodies;
	}

	public double getAverageBodyRadius() {
		return avgRadius.getAvg();
	}

	public double averageKineticWithinBounds(double[] bounds) {
		if (bodies == null)
			return 0;
		Average a = new Average();
		Rectangle rect = new Rectangle (
			(int)bounds[0],(int)bounds[1],
			(int)bounds[2],(int)bounds[3]
		);
		for (Body b : bodies)
			if (b.intersects(rect))
				a.add(b.getKineticEnergy());
		return a.getAvg();
	}
}
