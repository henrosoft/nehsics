package nehsics.test;
import nehsics.world.*;
import nehsics.ui.*;
import nehsics.force.*;
import nehsics.bodies.*;
import java.awt.*;
import static nehsics.math.Util.*;
import java.util.*;
public class TestOneD extends Tester {
	public final static String NAME = "Rotating Triangle";
	private Bonder bonder;

	public static void main(String[] args) {
		new Starter("nehsics.test.TestOneD");
	}

	public TestOneD(Canvas c) {
		super(c);
	}

	protected void setup() {
		FieldManager f = new FieldManager();
		Stats s = new Stats();
		world.addListener(s);
		world.addListener(f);
		world.addListener(new Gravitation(f));
		world.addListener(new Collider(s));
		bonder = new Bonder();
		world.addListener(bonder);
		Circle c1;
		Circle c2;
		Circle c3;
		world.addBody(c1 = new Circle(10,10));	
		c1.setPosition(v(20,00));
		world.addBody(c2 = new Circle(10,10));	
		c2.setColor(Color.green);
		c2.setPosition(v(0,-10));
		world.addBody(c3 = new Circle(10,10));	
		c3.setColor(Color.red);
		c3.setPosition(v(0,-30));
		BindingForce b2 = new BindingForce(c1,c2);	
		BindingForce b1 = new BindingForce(c2,c1);	
		BindingForce b3 = new BindingForce(c2,c3);	
		BindingForce b4 = new BindingForce(c3,c2);	
		BindingForce b5 = new BindingForce(c1,c3);	
		BindingForce b6 = new BindingForce(c3,c1);	

		c1.addBond(b6,c3);
		c3.addBond(b5,c1);
		bonder.addBond(b6);
		bonder.addBond(b5);


		c2.addBond(b2,c1);
		c3.addBond(b3,c2);
		bonder.addBond(b2);
		bonder.addBond(b3);
		bonder.addBond(b4);
		c2.addBond(b4,c3);
		c1.addBond(b1,c2);
		bonder.addBond(b1);
//		createFilament();
//		world.addBody(c1 = new Circle(10,10));
//		c1.setPosition(v(-500,90));
//		c1.setVelocity(v(90,0));
	double temp = 100;
		Circle c;
	        for (int i = 0; i < 9; i++)
       		     for (int j = 0; j < 9; j++) {
			world.addBody(c = new Circle(5,5));	
			c.setPosition(v(50+15*i-150, 50+15*j-150));
			c.setVelocity(v(temp*(Math.random()-.5), temp*(Math.random()-.5)));
		//	c.setTempColorEnabled(true,world);
		}	
//		world.addBody(c1 = new Circle(10,10));
//		c1.setPosition(v(100,10));
//		c1.setVelocity(v(-50,0));
	}

	public void createFilament() {
		ArrayList<Circle> circles = new ArrayList<Circle>();
		Circle c;
		for(int i = 0; i<10; i++) {
			world.addBody(c = new Circle(10,10));
			c.setPosition(v(0,i*20));
			circles.add(c);
		}
		BindingForce b;
		for(int i = 0; i<10; i++) {
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
}
