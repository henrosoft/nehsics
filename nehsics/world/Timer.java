package nehsics.world;

public class Timer {
	private final double target_ns;
	private long old_ns, now_ns, old_diff, now_diff, sleep_ms;
	private long printTime = System.currentTimeMillis();
	private long calcTime = System.nanoTime();
	private Average avg = new Average();

	private class Average {
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

	/**
	 * @param targetFPS The framerate this timer will try to keep.
	 */
	public Timer(double targetFPS) {
		target_ns = 1e9 / targetFPS;
		reset();
	}

	/**
	 * Reset the timer, as if newly constructed.
	 */
	public void reset() {
		avg.clear();
		old_ns = System.nanoTime();
		now_ns = old_ns;
		now_diff = old_diff = 0;
		sleep_ms = 0;
	}

	/**
	 * Step into the next frame.
	 * @return The approximate dt in seconds for the last tick.
	 */
	public double tick() {
		now_ns = System.nanoTime();
		now_diff = now_ns - old_ns;
		if (now_diff - old_diff > 1e7)
			sleep_ms = 0;
		else if (now_diff < target_ns)
			sleep_ms++;
		else if (sleep_ms > 0)
			sleep_ms--;
		old_diff = now_diff;
		avg.add(System.nanoTime() - calcTime);
		try {
			Thread.sleep(sleep_ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		double frame_ns = now_ns - old_ns;
		old_ns = now_ns;
		if (System.currentTimeMillis() - printTime > 1000) {
			System.out.println("Calculation time: " + (int)(avg.getAvg()/1e6) + "ms");
			avg.clear();
			printTime = System.currentTimeMillis();
		}
		calcTime = System.nanoTime();
		return frame_ns/1e9;
	}
}
