package nehsics;
import java.util.*;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.geom.*;
public class QuadTree{
	private List<QuadTree> links;
	private List<Body> bodies;
	private Vector2d maxP;
	private Vector2d minP;
	public QuadTree(List<QuadTree> l, List<Body> b, Vector2d minp, Vector2d maxp)
	{
		links = l;
		bodies = b;
		this.maxP = maxp;
		this.minP = minp;
	}
	public void addBody(Body b)
	{
		bodies.add(b);
	}
	public void checkForCollisions()
	{
		System.out.println(minP + " " + maxP + " " + bodies.size());	
		if(bodies.size() <= 1)
			return;
		else if(bodies.size() == 2){
			if(((Circle)bodies.get(0)).intersects((Circle)bodies.get(1)))
				((Circle)bodies.get(0)).hit((Circle)bodies.get(1));
		}
		else
			devide();
	}
	public boolean contains(Body b)
	{
		Rectangle2D r = new Rectangle2D.Double(minP.getX(), minP.getY(), maxP.getX(), maxP.getY());
		return b.intersectsRectangle(r);
	}
	public void devide()
	{	
		double midX = (maxP.getX()+minP.getX())/2.0;
		double midY = (maxP.getY()+minP.getY())/2.0;
		QuadTree q1 = new QuadTree(new LinkedList<QuadTree>(), new LinkedList<Body>(), v(midX,midY), v(maxP.getX(),maxP.getY()));
		QuadTree q2 = new QuadTree(new LinkedList<QuadTree>(), new LinkedList<Body>(), v(minP.getX(),midY), v(midX,maxP.getY()));
		QuadTree q3 = new QuadTree(new LinkedList<QuadTree>(), new LinkedList<Body>(), v(minP.getX(), minP.getY()), v(midX, midY));
		QuadTree q4 = new QuadTree(new LinkedList<QuadTree>(), new LinkedList<Body>(), v(midX, minP.getY()), v(maxP.getX(), midY));
		links.add(q1);
		links.add(q2);
		links.add(q3);
		links.add(q4);
		for(Body b: bodies)
			for(QuadTree q: links)
				if(q.contains(b))
					q.addBody(b);
		for(QuadTree q: links)
			q.checkForCollisions();
	}
}
