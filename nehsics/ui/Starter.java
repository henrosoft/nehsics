package nehsics.ui;
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
	protected Scene t;

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

		SceneConstructor initCon = new SceneConstructor(init);
		boolean foundSelection = false;
		for (SceneConstructor testCon : getSceneConstructors()) {
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
			SceneConstructor logoCon = new SceneConstructor("nehsics.ui.Logo");
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
					try {
						t.quit();
					} catch (Exception t) {
						t.printStackTrace();
					}
				}
				t = ((SceneConstructor)combo.getSelectedItem()).newInstance(canvas);
				t.start();
			}
		});
		t = initCon.newInstance(canvas);
		t.start();
	}

	private List<SceneConstructor> getSceneConstructors() {
		List<SceneConstructor> cons = new LinkedList<SceneConstructor>();
		File dir = new File(getClass().getResource("/nehsics/test/").getFile());
		String[] tests = dir.list();
		if (tests == null) // in a jar file :(
			tests = readScenesFromJar();
		for (String test : tests) {
			if (!test.endsWith(".class") || test.contains("$"))
				continue;
			String name = "nehsics.test." + test.split(".class")[0];
			try {
				cons.add(new SceneConstructor(name));
			} catch (Exception e) {}
		}
		return cons;
	}

	private String[] readScenesFromJar() {
		List<String> tests = new LinkedList<String>();
		try {
			JarFile jar = new JarFile(getClass().getProtectionDomain()
				.getCodeSource().getLocation().getPath());
			System.out.println(jar);
			for (Enumeration<JarEntry> e = jar.entries(); e.hasMoreElements();) {
				String name = e.nextElement().toString();
				if (name.matches("nehsics/test/[^$]*.class"))
					tests.add(name.split("nehsics/test/")[1]);
			}
			jar.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tests.toArray(new String[0]);
	}

	public static void main(String[] args) {
		new Starter("nehsics.ui.Logo");
	}

	/**
	 * Constructor wrapper for instantiating Scenes, provided
	 * that they have a suitable constructor.
	 * The NAME field for tests is optional.
	 */
	private class SceneConstructor {
		private String name;
		private Constructor<Scene> cons;

		@SuppressWarnings(value = "unchecked")
		public SceneConstructor(String init) {
			try {
				Class<Scene> test = (Class<Scene>)Class.forName(init);
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

		public boolean equals(SceneConstructor other) {
			return other.cons.getDeclaringClass().equals(cons.getDeclaringClass());
		}

		public Scene newInstance(Canvas canvas) {
			Scene test = null;
			try {
				test = cons.newInstance(canvas);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return test;
		}
	}
}
