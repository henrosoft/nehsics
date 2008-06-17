package nehsics.ui;
import nehsics.test.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.jar.*;

public class Starter {
	protected JFrame frame;
	protected JSplitPane jsplit;
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

		TestConstructor initCon = new TestConstructor(init);
		boolean foundSelection = false;
		for (TestConstructor testCon : getTestConstructors()) {
			combo.addItem(testCon);
			if (testCon.equals(initCon)) {
				combo.setSelectedItem(testCon);
				foundSelection = true;
			}
		}
		if (!foundSelection) { // init must not be in the list, add it manually
			combo.insertItemAt(initCon, 0);
			combo.setSelectedItem(initCon);
		} else { // otherwise tack on the logo
			TestConstructor logoCon = new TestConstructor("nehsics.ui.Logo");
			combo.insertItemAt(logoCon, 0);
		}

		frame.setContentPane(jsplit);
		frame.setIgnoreRepaint(true);
		frame.setVisible(true);
		final KeyboardFocusManager manager =
			KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				manager.redispatchEvent(canvas, e);
				if (!Character.isLetter(e.getKeyChar()))
					manager.redispatchEvent(combo, e);
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
				t = ((TestConstructor)combo.getSelectedItem()).newInstance(canvas);
				t.start();
			}
		});
		t = initCon.newInstance(canvas);
		t.start();
	}

	private List<TestConstructor> getTestConstructors() {
		List<TestConstructor> cons = new LinkedList<TestConstructor>();
		File dir = new File(getClass().getResource("/nehsics/test/").getFile());
		String[] tests = dir.list();
		if (tests == null) // in a jar file :(
			tests = readTestsFromJar();
		for (String test : tests) {
			if (!test.endsWith(".class") || test.contains("$"))
				continue;
			String name = "nehsics.test." + test.split(".class")[0];
			try {
				cons.add(new TestConstructor(name));
			} catch (Exception e) {}
		}
		return cons;
	}

	private String[] readTestsFromJar() {
		List<String> tests = new LinkedList<String>();
		try {
			JarFile jar = new JarFile("NEHsics.jar");
			for (Enumeration<JarEntry> e = jar.entries(); e.hasMoreElements();) {
				String name = e.nextElement().toString();
				if (name.matches("nehsics/test/[^$]*.class"))
					tests.add(name.split("nehsics/test/")[1]);
			}
			jar.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return tests.toArray(new String[0]);
	}

	public static void main(String[] args) {
		new Starter("nehsics.ui.Logo");
	}

	/**
	 * Constructor wrapper for instantiating Tests, provided
	 * that they have a suitable constructor.
	 * The NAME field for tests is optional.
	 */
	private class TestConstructor {
		private String name;
		private Constructor<Test> cons;

		@SuppressWarnings(value = "unchecked")
		public TestConstructor(String init) {
			try {
				Class<Test> test = (Class<Test>)Class.forName(init);
				cons = test.getConstructor(Class.forName("java.awt.Canvas"));
				name = (String)test.getField("NAME").get(null);
			} catch (Exception e) {
				throw new IllegalArgumentException("Bad class '" + init + "'");
			} finally {
				if (name == null && cons != null)
					name = cons.toString();
			}
		}

		public String toString() {
			return name;
		}

		public boolean equals(TestConstructor other) {
			return other.cons.getDeclaringClass().equals(cons.getDeclaringClass());
		}

		public Test newInstance(Canvas canvas) {
			Test test = null;
			try {
				test = cons.newInstance(canvas);
			} catch (Exception e) {
				System.err.println(e);
			}
			return test;
		}
	}
}
