package nehsics.world;
import java.awt.*;
import nehsics.bodies.*;

public abstract class WorldAdapter implements WorldListener {
	public void beginStep(World world, double dt) {}
	public void endStep(World world, double dt) {}
	public void newBody(World world, Body b) {}
	public void paint(Graphics2D g2d) {}
}
