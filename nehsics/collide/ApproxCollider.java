package nehsics.collide;
import nehsics.bodies.*;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

/**
 * Fast in-place quadtree that discards 1/2 of all overlaps (FIXME)
 * Also quicksort is probably a bad idea for this task (FIXME)
 * This is of some use with fluid simulations, since we don't
 * really care if particles collide exactly right.
 */
public class ApproxCollider {
	private List<Integer> indicies = new LinkedList<Integer>();
	private final int bucketSize; // lower == insanity
	private Set<Line2D> visuals = new HashSet<Line2D>();

	public ApproxCollider(int precision) {
		bucketSize = precision;
	}

	public void resolveCollisions(List<Body> tree) {
		indicies.clear();
		visuals.clear();
		quickSortV(tree, 0, tree.size()-1);
		indicies.add(tree.size());
		Collections.sort(indicies);
		int first = 0;
		for (int last : indicies) {
			resolveCollisions(tree, first, last);
			first = last;
		}
	}

	private void quickSortV(List<Body> tree, int first, int last) {
		if (last - first < bucketSize)
			return;
		int g = first, h = last;
		int midIndex = (first + last) / 2;
		double dividingValue = tree.get(midIndex).getPosition().getX();

		visuals.add(new Line2D.Double(dividingValue, -32000,
		                              dividingValue, 32000));

		do {
			while (tree.get(g).getPosition().getX() < dividingValue)
				g++;
			while (tree.get(h).getPosition().getX() > dividingValue)
				h--;
			if (g <= h)
				Collections.swap(tree, g++, h--);
		} while (g < h);
		indicies.add(g);

		if (h > first)
			quickSortH(tree,first,h);
		if (g < last)
			quickSortH(tree,g,last);
	}

	private void quickSortH(List<Body> tree, int first, int last) {
		if (last - first < bucketSize)
			return;
		int g = first, h = last;
		int midIndex = (first + last) / 2;
		double dividingValue = tree.get(midIndex).getPosition().getY();

		visuals.add(new Line2D.Double(-32000, dividingValue,
		                              32000, dividingValue));
		do {
			while (tree.get(g).getPosition().getY() < dividingValue)
				g++;
			while (tree.get(h).getPosition().getY() > dividingValue)
				h--;
			if (g <= h)
				Collections.swap(tree, g++, h--);
		} while (g < h);
		indicies.add(g);

		if (h > first)
			quickSortV(tree,first,h);
		if (g < last)
			quickSortV(tree,g,last);
	}

	private void resolveCollisions(List<Body> tree, int first, int last) {
		Body a, b;
		for (int i = first; i < last; i++) {
			a = tree.get(i);
			for (int j=i+1; j < last; j++) {
				b = tree.get(j);
				if (a.canHit(b))
					a.hit(b);
			}
		}
	}

	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		for (Line2D line : visuals)
			g2d.draw(line);
	}
}
