package nehsics;
import java.awt.*;
import java.awt.image.*;

public class Display {
	private BufferStrategy strategy;
	private Graphics2D buf;
	private Canvas canvas;
	private double scale = 1; // scale is set by the program
	private double zoom = 1; // zoom is controlled by the user
	private boolean fade, clear;
	private int x, y;

	public Display(Canvas c) {
		canvas = c;
		canvas.setIgnoreRepaint(true);
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		clearBuffer();
	}

	public void reset() {
		zoom = scale = 1;
		fade = clear = false;
		x = y = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int newX) {
		x = newX;	
		clear = true;
	}

	public void setY(int newY) {
		y = newY;
		clear = true;
	}
	
	public void centerDisplay(double newx, double newy) {
		x = - (int) (newx * scale * zoom); // + frame.getWidth()/2;
		y = - (int) (newy * scale * zoom);
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
		clearBuffer();
		buf.scale(scale*zoom, scale*zoom);
	}

	public void clearBuffer() {
		buf = (Graphics2D)strategy.getDrawGraphics();
		buf.setColor((fade && !clear) ? new Color(255,255,255,50) : Color.WHITE);
		clear = false;
		buf.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		buf.translate(x+canvas.getWidth()/2,
			y+canvas.getHeight()/2);
		buf.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
