package nehsics.ui;

public abstract class Scene extends Thread {
	/** Set to false to stop the scene */
	protected volatile boolean running = true;

	/**
	 * Signals thread to stop and blocks until it does.
	 */
	public void quit() throws InterruptedException {
		running = false;
		this.join();
	}
}
