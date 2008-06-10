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
	protected Test t;

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
		combo.addItem(LogoTest.NAME);
		combo.addItem(OrbitTest.NAME);
		combo.addItem(Tester.NAME);
		combo.addItem(SquareTester.NAME);
		combo.addItem(BrownianMotionTester.NAME);
		combo.addItem(GasTest.NAME);
		combo.addItem(CannonTester.NAME);
		frame.setContentPane(jsplit);
		frame.setIgnoreRepaint(true);
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
				if (t != null) {
					t.quit();
					try {
						t.join();
					} catch (Exception t) {
						t.printStackTrace();
					}
				}
				t = makeTester((String)combo.getSelectedItem());
				t.start();
			}
		});
		t = new LogoTest(canvas);
		t.start();
	}

	private Test makeTester(String name) {
		if (name.equals(OrbitTest.NAME))
			return new OrbitTest(canvas);
		else if (name.equals(Tester.NAME))
			return new Tester(canvas);
		else if (name.equals(SquareTester.NAME))
			return new SquareTester(canvas);
		else if (name.equals(BrownianMotionTester.NAME))
			return new BrownianMotionTester(canvas);
		else if (name.equals(GasTest.NAME))
			return new GasTest(canvas);
		else if (name.equals(CannonTester.NAME))
			return new CannonTester(canvas);
		return new LogoTest(canvas);
	}

	public static void main(String[] args) {
		new Starter();
	}
}
