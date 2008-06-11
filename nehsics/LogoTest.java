package nehsics;
import java.awt.*;

public class LogoTest extends Test {
	private final static Font f = new Font("Serif", Font.BOLD, 50);
	public final static String NAME = "NEHSICS";
	protected volatile boolean running = true;
	private Display display;

	public LogoTest(Canvas c) {
		display = new Display(c);
	}

	public void run() {
		while (running) { // XXX can't be bothered to use paint() properly
			repaint();
			try {
				Thread.sleep(100);
			} catch (Exception e) {}
		}
	}

	public void repaint() {
		display.show();
		display.clear();
		Graphics2D g2d = display.getGraphics();
		g2d.setColor(Color.BLACK);
		g2d.setFont(f);
		g2d.drawString("NEHsics", -93, 20);
		display.show();
	}

	public void quit() {
		running = false;
	}
}
