package nehsics.world;
import java.awt.*;
import nehsics.bodies.*;

public interface WorldListener {

	/**
	 * Notification that the world has begun its step.
	 * No bodies have been stepped before this event.
	 */
	public void beginStep(World world, double dt);

	/**
	 * Notification that the world has finished its step.
	 * All bodies have been stepped before this event.
	 */
	public void endStep(World world, double dt);
	
	/**
	 * Notification that a body has been added to the world.
	 * The body has been added to bodies before this event.
	 */
	public void newBody(World world, Body b);

	/**
	 * Paint event for the world.
	 * Listeners will be called in the order they are added.
	 */
	public void paint(Graphics2D g2d);
}
