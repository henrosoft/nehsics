package nehsics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;

public class Starter {
	protected JFrame frame;
	protected JSplitPane jsplit;
	protected JComboBox combo;
	protected String s;
	protected Canvas canvas;
	protected Tester t;

	public Starter() {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setTitle("NEHsics");
		combo = new JComboBox();
		combo.setLightWeightPopupEnabled(false);
		canvas = new Canvas();
		jsplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, canvas, combo);
		// for some reason Integer.MAX_VALUE doesn't work
		canvas.setMinimumSize(new Dimension(30000,30000));
		jsplit.setDividerLocation(432);
		jsplit.setResizeWeight(1);
		combo.addItem("Orbit test");
		combo.addItem("Gravity test");
		combo.addItem("Square test");
		combo.addItem("Gas test");
		combo.addItem("Cannon test");
		frame.setContentPane(jsplit);
		frame.setVisible(true);
		final KeyboardFocusManager manager =
			KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getSource() == canvas)
					return false;
				manager.redispatchEvent(canvas, e);
				return true;
			}
		});
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (t != null)
					t.quit();
				t = makeTester((String)combo.getSelectedItem());
				t.start();
			}
		});
	}

	private Tester makeTester(String name) {
		if (name.equals("Orbit test"))
			return new OrbitTest(canvas);
		else if (name.equals("Gravity test"))
			return new Tester(canvas);
		else if (name.equals("Square test"))
			return new SquareTester(canvas);
		else if (name.equals("Gas test"))
			return new GasTest(canvas);
		else if (name.equals("Cannon test"))
			return new CannonTester(canvas);
		return null;
	}

	public static void main(String[] args) {
		new Starter();
	}
}
