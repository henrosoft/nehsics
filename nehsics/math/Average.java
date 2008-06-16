package nehsics.math;

public class Average {
	private double a;
	private int n;
	
	public void add(double x) {
		n++;
		a -= a/n - x/n;
	}
	
	public double getAvg() {
		return a;
	}

	public double getNum() {
		return n;
	}

	public void clear() {
		a = n = 0;
	}
}
