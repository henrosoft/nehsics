package nehsics.ui;
import java.awt.*;

public class Logo extends Scene {
	private final static Font f = new Font("Serif", Font.BOLD, 50);
	public final static String NAME = "NEHSICS";
	private Display display;

	public static void main(String[] args) {
		new Starter("nehsics.ui.Logo");
	}

	public Logo(Canvas c) {
		display = new Display(c);
		display.enableAA();
	}

	public void run() {
		while (running) {
			repaint(); // XXX can't be bothered to use paint() properly
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
