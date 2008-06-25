package nehsics.world;

import java.awt.*;

import java.util.List;

import nehsics.bodies.*;

public abstract class WorldAdapter implements WorldListener {
	public void beginStep(List<Body> bodies, double dt) {}
	public void endStep(List<Body> bodies, double dt) {}
	public void newBody(List<Body> bodies, Body b) {}
	public void paint(Graphics2D g2d) {}
}
