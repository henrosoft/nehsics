package nehsics;
import java.awt.*;
import java.awt.image.*;

public class Display {
	protected BufferStrategy strategy;
	protected Graphics2D buf;
	protected Canvas canvas;
	protected double scale = 1; // scale is set by the program
	protected double zoom = 1; // zoom is controlled by the user
	protected boolean fade;
	public int x, y;

	public Display(Canvas c) {
		canvas = c;
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		buf = (Graphics2D)strategy.getDrawGraphics();
	}

	
	public void centerDisplay(double newx, double newy) {
		x = - (int) (newx * zoom);// + frame.getWidth()/2;
		y = - (int) (newy * zoom);
	}

	public void setScale(double s) {
		scale = s;
	}

	public void zoomIn() {
		zoom *= 1.2;
	}

	public void zoomOut() {
		zoom /= 1.2;
	}

	public void zoomDefault() {
		zoom = 1;
	}

	public void setFadeEnabled(boolean b) {
		fade = b;
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
		buf.setColor(fade ? new Color(255,255,255,50) : Color.WHITE);
		buf.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		buf.translate(x+canvas.getWidth()/2,
			y+canvas.getHeight()/2);
		buf.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
