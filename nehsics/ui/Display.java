package nehsics.ui;
import nehsics.world.*;
import nehsics.bodies.*;
import static nehsics.math.Util.*;
import nehsics.math.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class Display extends WorldAdapter {
	private BufferStrategy strategy;
	private Graphics2D buf;
	private Canvas canvas;
	private double scale = 1; // scale is set by the program
	private double zoom = 1; // zoom is controlled by the user
	private boolean fade, clear;
	private double x, y;
	private Body center, manualCenter;
	private LinkedList<Body> targets = new LinkedList<Body>();

	public Display(Canvas c) {
		canvas = c;
		canvas.setIgnoreRepaint(true);
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		clear();
	}

	/**
	 * Reset all parameters (restart test)
	 */
	public void reset() {
		synchronized (targets) {
			targets.clear();
		}
		center = manualCenter = null;
		zoom = scale = 1;
		fade = clear = false;
		x = y = 0;
	}

	public int h() {
		return (int)canvas.getSize().getHeight();
	}

	public int w() {
		return (int)canvas.getSize().getWidth();
	}

	public void unsetBufTransforms() {
		buf.setTransform(new AffineTransform());
	}

	public void move(Vector2d disp) {
		x += disp.getX();	
		y += disp.getY();
	}

	public void setTrackedBody(Body b) {
		center = manualCenter = b;
	}

	/**
	 * Reset user modifications except for tracking order: TODO
	 */
	public void softReset() {
		x = y = 0;
		zoom = 1;
		center = manualCenter;
	}

	public void trackNext() {
		synchronized (targets) {
			if (targets == null || targets.size() < 1)
				return;
			x = y = 0;
			targets.addLast(targets.removeFirst());
			center = targets.getFirst();
		}
	}

	public void trackPrevious() {
		synchronized (targets) {
			if (targets == null || targets.size() < 1)
				return;
			x = y = 0;
			targets.addFirst(targets.removeLast());
			center = targets.getFirst();
		}
	}

	public void newBody(World world, Body b) {
		synchronized (targets) {
			targets.addLast(b);	
		}
	}

	public void setScale(double s) {
		scale = s;
		clear = true;
	}

	public void zoomIn() {
		zoom *= 1.2;
		clear = true;
	}

	public void zoomOut() {
		zoom /= 1.2;
		clear = true;
	}

	public void zoomDefault() {
		zoom = 1;
		clear = true;
	}

	public void toggleFade() {
		fade = !fade;
	}

	public void drawWorld(World world) {
		world.paint(buf);
	}

	public Graphics2D getGraphics() {
		return buf;
	}

	public void show() {
		buf.dispose();
		strategy.show();
	}

	public void clear() {
		Vector2d v2d = v();
		if (center != null)
			v2d = scale(center.getPosition(), -scale*zoom);
		buf = (Graphics2D)strategy.getDrawGraphics();
		buf.setColor((fade && !clear) ? new Color(255,255,255,50) : Color.WHITE);
		clear = false;
		buf.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		buf.translate(v2d.getX()+x+(double)canvas.getWidth()/2,
			v2d.getY()+y+(double)canvas.getHeight()/2);
		buf.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		buf.scale(scale*zoom, scale*zoom);
	}
}
