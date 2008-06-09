package nehsics;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;

public class Starter implements Runnable{
	protected JFrame frame;
	protected JComboBox combo;
	protected String s;
	protected Thread t;
	public Starter() {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setTitle("NEHsics");
		combo = new JComboBox();
		combo.addItem("Orbit test");
		combo.addItem("Gravity test");
		combo.addItem("Square test");
		combo.addItem("Gas test");
		combo.addItem("Cannon test");
		frame.add(combo);
		frame.setVisible(true);
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				s = (String)combo.getSelectedItem();
				start();
			}
		});
	}
	public void start()
	{
		t = new Thread(this);
		t.start();
	}
	public void run()
	{
		if(s.equals("Orbit test"))
			new OrbitTest();
		if(s.equals("Gravity test"))
			new Tester();
		if(s.equals("Square test"))
			new SquareTester();
		if(s.equals("Gas test"))
			new GasTest();
		if(s.equals("Cannon test"))
			new CannonTester();
	}
	public static void main(String[] args) {
		new Starter();
	}
}
