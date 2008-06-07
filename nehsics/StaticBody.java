package nehsics;
import nehsics.math.*;
import static nehsics.math.Util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class StaticBody extends Body {

	public StaticBody(Vector2d pos, Shape s) {
		super(pos, v(), Double.POSITIVE_INFINITY, s);
	}

    public void step() {
		// nothing		
	}
}
