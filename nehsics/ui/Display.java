package nehsics.ui;
import nehsics.world.*;
import nehsics.bodies.*;
import static nehsics.math.Util.*;
import nehsics.math.*;
import java.awt.*;
import java.awt.image.*;

public class Display {
	private BufferStrategy strategy;
	private Graphics2D buf;
	private Canvas canvas;
	private double scale = 1; // scale is set by the program
	private double zoom = 1; // zoom is controlled by the user
	private boolean fade, clear;
	private double x, y;
	private Body center;

	public Display(Canvas c) {
		canvas = c;
		canvas.setIgnoreRepaint(true);
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		clear();
	}

	public void reset() {
		center = null;
		zoom = scale = 1;
		fade = clear = false;
		x = y = 0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double newX) {
		x = newX;	
		clear = true;
	}

	public void setY(double newY) {
		y = newY;
		clear = true;
	}

	public void setTrackedBody(Body b) {
		center = b;
		clear = true;
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

	public void setFadeEnabled(boolean b) {
		fade = b;
	}

	public boolean getFadeEnabled() {
		return fade;
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
