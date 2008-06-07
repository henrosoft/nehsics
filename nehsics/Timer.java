package nehsics;

public class Timer {
	private final double target_ns;
	private long old_ns, now_ns, old_diff, now_diff, sleep_ms;

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
		try {
			Thread.sleep(sleep_ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		double frame_ns = now_ns - old_ns;
		old_ns = now_ns;
		return frame_ns/1e9;
	}
}
