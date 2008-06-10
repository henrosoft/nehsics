package nehsics;
import java.awt.*;
import java.awt.event.*;

public class LogoTest extends Test {
	private final static Font f = new Font("Serif", Font.BOLD, 50);
	public final static String NAME = "NEHSICS";
	private Display display;

	public LogoTest(Canvas c) {
		display = new Display(c);
		c.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				repaint();
			}
		});
		repaint();
	}

	public void repaint() {
		display.show();
		Graphics2D g2d = display.getGraphics();
		g2d.setColor(Color.BLACK);
		g2d.setFont(f);
		g2d.drawString("NEHsics", -93, 20);
		display.show();
	}

	public void quit() {
		// already done...	
	}
}
