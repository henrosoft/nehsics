package nehsics;
import static nehsics.math.Util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.text.JTextComponent;
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
	}
	public static void main(String[] args) {
		new Starter();
	}
}
