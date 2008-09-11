package nehsics.test;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.bodies.*;
import nehsics.force.*;
import java.awt.*;
import static nehsics.math.Util.*;
import java.util.*;
import java.awt.event.*;

public class GasTest extends UserControlledScene {
	public final static String NAME = "Ideal Gas Model";
	private Bonder bonder;
	private Stats stats;
	private Walls walls;

	public static void main(String[] args) {
		new Starter("nehsics.test.GasTest");
	}

	public GasTest(Canvas c) {
		super(c);
	}

	public void createFilament() {
		ArrayList<Circle> circles = new ArrayList<Circle>();
		Circle x;
		for(int i = 0; i<100; i++) {
			x = new Circle(10, 10);
			x.setPosition(v(250, 50));
			x.setVelocity(v(0,0));
			world.addBody(x);
			circles.add(x);
		}
		BindingForce b;
		for (int i = 0; i<10; i++) {
			if (i!=0) {
				b = new BindingForce(circles.get(i-1),circles.get(i));
				bonder.addBond(b);
				circles.get(i).addBond(b,circles.get(i-1));
			}
			if (i!=9) {
				b = new BindingForce(circles.get(i+1),circles.get(i));
				bonder.addBond(b);
				circles.get(i).addBond(b,circles.get(i+1));
			}
		}
	}

	protected void setupDisplay() {
		Circle c;
		display.setTrackedBody(c = new Circle(0,0));
		c.setPosition(v(0,-100));
	}

	protected void setup() {
		canvas.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_P) {
					Body c = new Circle(8,5);
					c.setPosition(v(400, 0));
					c.setVelocity(v(-1000,0));
					c.setTempColorEnabled(true, world);
					world.addBody(c);
				}
			}
		});
		stats = new Stats();
		world.addListener(stats);
		world.addListener(bonder = new Bonder());
		world.addListener(new BadCollider(30));
		walls = new Walls(250,.95);
		world.addListener(walls);
		display.setScale(.4);
		int temp = 100;
		Circle c;
		for (int i = 0; i < 19; i++)
			for (int j = 0; j < 19; j++) {
				world.addBody(c = new Circle(8,5));	
				c.setPosition(v(26*i-230, 26*j-230));
				c.setVelocity(v(temp*(Math.random()-.5), temp*(Math.random()-.5)));
				c.setTempColorEnabled(true, world);
			}	
	}

	public void postWorld() {
		super.postWorld();
		Graphics2D g2d = display.getGraphics();
		double fraction = walls.getInternalEnergy()/3e7;
		g2d.setColor(getColor(fraction));
		g2d.fillRect(-290,-450,40,710);
		g2d.fillRect(-290,250,540,40);
		g2d.fillRect(250,-450,50,440);
		g2d.fillRect(250,8,50,280);
		g2d.fillRect(-245,-290,490,40);
		g2d.fillRect(-20,-400,40,130);
		g2d.fillRect(400,-15,100,30);
	}

	public void overlay() {
		super.overlay();
		Graphics2D g2d = display.getGraphics();
		g2d.setColor(Color.BLACK);
		double ave = stats.averageKineticWithinBounds(
			new double[]{-250,-250,500,500})/1000;
		double fraction = ave/200.0;
		g2d.setFont(new Font(null,Font.BOLD,10));
		g2d.drawString("Temperature = " + (int)ave + " Kelvin", 5, 10);
		g2d.drawString("P - fire particle",400,420);
		g2d.setColor(getColor(fraction));
		g2d.fillRect(5,15,(int)(fraction*300),10);
	}

	public Color getColor(double f) {
		double r=0,g=0,b=0;
		if(f < .2)
			b = 255*5*f;
		else if (f < .4) {
			b = 255;
			g = 255*5*(f-.2);
		} else if(f < .5) {
			g = 255;
			b = 255-(255*10*(f-.4));
		} else if(f < .6) {
			g = 255;
			r = (255*10*(f-.5));
		} else if(f <= 1) {
			r = 255;
			g = 255-(255*10*(f-.6)/4.0);	
		} else
			r = 255;
		return new Color((int)r,(int)g,(int)b);
	}
}
