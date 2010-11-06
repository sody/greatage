package org.greatage.util;

/**
 * This class represent utility that locks some code to prevent use it again.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Locker {
	private boolean lock;

	/**
	 * Checks if instance is locked.
	 *
	 * @throws IllegalStateException if instance is locked
	 */
	public synchronized void check() {
		if (lock) {
			throw new IllegalStateException("Locked");
		}
	}

	/**
	 * Locks instance to prevent use it again.
	 *
	 * @throws IllegalStateException if instance is locked
	 */
	public synchronized void lock() {
		check();
		lock = true;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("locked", lock);
		return builder.toString();
	}
}
