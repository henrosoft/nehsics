package nehsics.test;
import nehsics.collide.*;
import nehsics.ui.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;

public class GasTest extends Tester {
	public final static String NAME = "Ideal Gas Model";

	public static void main(String[] args) {
		new Starter(NAME);
	}

	public GasTest(Canvas c) {
		super(c);
	}

	protected void setup() {
		PRECISION = 1;
		world.setWallsEnabled(true);
		display.setScale(.3);
		world.setGravityEnabled(false);
	        int temp = 100;
		Circle c;
	        for (int i = 0; i < 19; i++)
       		     for (int j = 0; j < 19; j++) {
			world.addBody(c = new Circle(5,5));	
			c.setPosition(v(50+15*i-150, 50+15*j-150));
			c.setVelocity(v(temp*(Math.random()-.5), temp*(Math.random()-.5)));
			c.setTempColorEnabled(true,world);
		}	
		for (int i = 0; i<4; i++) {
				world.addBody(c = new Circle(5,5));	
				c.setPosition(v(5000+25*i, 0));
				c.setVelocity(v(1000, 0));
				c.setTempColorEnabled(true,world);
		}
	}

	public void postWorld() {
		super.postWorld();
		Graphics2D g2d = display.getGraphics();
		g2d.setColor(Color.BLACK);
		double ave = world.averageKineticWithinBounds(
			new double[]{-250,-250,500,500})/1000;
		double fraction = ave/200.0;
		g2d.setFont(new Font(null,Font.BOLD,30));
		g2d.drawString("Temperature = " + (int)ave + " Kelvin", -50, -670);
		g2d.setColor(getColor(fraction));
		g2d.fillRect(-50,-665,(int)(fraction*300),30);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(-290,-450,40,710);
		g2d.fillRect(-290,250,540,40);
		g2d.fillRect(250,-450,50,440);
		g2d.fillRect(250,10,50,280);

		g2d.fillRect(-245,-290,490,40);
		g2d.fillRect(-20,-400,40,130);
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
