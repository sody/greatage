package org.greatage.util;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MultiLocker<K> {
	private final Map<K, Locker> lockers = CollectionUtils.newConcurrentMap();

	public synchronized void check(final K key) {
		if (lockers.containsKey(key)) {
			final Locker locker = lockers.get(key);
			locker.check();
		}
	}

	public synchronized void lock(final K key) {
		if (!lockers.containsKey(key)) {
			lockers.put(key, new Locker());
		}
		lockers.get(key).lock();
	}
}
