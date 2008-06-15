package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;

public class BindingForce extends SpringForce {
	private Body b1;
	private Body b2;
	private boolean broken = false;
	private Vector2d force = v(0,0);
	public BindingForce(Body a, Body b) {
		super(1000);
		b1 = a;
		b2 = b;
	}
	public void calculateForce()
	{
		if(distance(b1.getPosition(), b2.getPosition())>= (b1.getRadius()+b2.getRadius())*40)
			broken = true;
		if(((Circle)b1).canHitForce((Circle)b2) || broken)
		{
			force = v(0,0);
			return;
			}
		force = super.getForce(b1.getPosition(), b2.getPosition());
	}
	public Vector2d getForce() {
		return force;
	}
}
