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
		world.setGravityEnabled(false);
		world.setCollider(new Collider());
        int temp = 100;
		Circle c;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
				world.addBody(c = new Circle(10,10));	
				c.setPosition(v(50+25*i-150, 50+25*j-150));
				c.setVelocity(v(temp*(Math.random()-.5), temp*(Math.random()-.5)));
				c.setTempColorEnabled(true,world);
			}
	
		for(int i = 0; i<2; i++)
		{
				world.addBody(c = new Circle(10,10));	
				c.setPosition(v(5000+25*i, 0));
				c.setVelocity(v(1000, 0));
				c.setTempColorEnabled(true,world);
		}
	}
	public void postWorld() {
		super.postWorld();
		Graphics2D g2d = display.getGraphics();
		g2d.setColor(Color.BLACK);
		double ave = (world.averageKineticWithinBounds(new double[]{-250,-250,500,500})/1000);
		double fraction = ave/200.0;
		System.out.println(fraction);
		g2d.drawString("Temperature = " + (int)ave + " Kelvin", 30, -200);
		g2d.setColor(getColor(fraction));
		g2d.fillRect(30,-195,(int)(fraction*200),10);
	}
	public Color getColor(double f) {
		double r=0,g=0,b=0;
		if(f<.2)
			b = 255*5*f;
		else if(f<.4)
		{
			b = 255;
			g=255*5*(f-.2);
		}
		else if(f<.5)
		{
			g=255;
			b=255-(255*10*(f-.4));
		}
		else if(f<.6)
		{
			g=255;
			r=(255*10*(f-.5));
		}
		else if(f<=1)
		{
			r=255;
			g=255-(255*10*(f-.6)/4.0);	
		}
		else
			r = 255;
		return new Color((int)r,(int)g,(int)b);
	}
}
