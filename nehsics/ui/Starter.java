package nehsics.ui;
import nehsics.test.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;

public class Starter {
	protected JFrame frame;
	protected JSplitPane jsplit ;
	protected JComboBox combo;
	protected String s;
	protected Canvas canvas;
	protected Test t;

	public Starter(String init) {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(dim.getWidth()/2-250),(int)(dim.getHeight()/2-250));
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
		combo.addItem(CollideTester.NAME);
		combo.addItem(BrownianMotionTester.NAME);
		combo.addItem(GasTest.NAME);
		combo.addItem(CannonTester.NAME);
		combo.addItem(CollapseTest.NAME);
		combo.addItem(TestOneD.NAME);
		combo.setSelectedItem(init);
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
		combo.addActionListener(new ActionListener() {
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
		t = makeTester(init);
		t.start();
	}

	private Test makeTester(String name) {
		if (OrbitTest.NAME.equals(name))
			return new OrbitTest(canvas);
		else if (Tester.NAME.equals(name))
			return new Tester(canvas);
		else if (CollideTester.NAME.equals(name))
			return new CollideTester(canvas);
		else if (BrownianMotionTester.NAME.equals(name))
			return new BrownianMotionTester(canvas);
		else if (GasTest.NAME.equals(name))
			return new GasTest(canvas);
		else if (CannonTester.NAME.equals(name))
			return new CannonTester(canvas);
		else if (CollapseTest.NAME.equals(name))
			return new CollapseTest(canvas);
		else if (TestOneD.NAME.equals(name))
			return new TestOneD(canvas);
		return new LogoTest(canvas);
	}

	public static void main(String[] args) {
		new Starter(LogoTest.NAME);
	}
}
