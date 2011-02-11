/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.util;

import java.util.Map;

/**
 * This class represent utility that locks some code by key to prevent use it again.
 *
 * @param <K> type of keys
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MultiLocker<K> {
	private final Map<K, Locker> lockers = CollectionUtils.newConcurrentMap();

	/**
	 * Checks if specified key is locked.
	 *
	 * @param key key
	 * @throws IllegalStateException if instance is locked
	 */
	public synchronized void check(final K key) {
		if (lockers.containsKey(key)) {
			final Locker locker = lockers.get(key);
			locker.check();
		}
	}

	/**
	 * Locks specified key to prevent use it again.
	 *
	 * @param key key
	 * @throws IllegalStateException if instance is locked
	 */
	public synchronized void lock(final K key) {
		if (!lockers.containsKey(key)) {
			lockers.put(key, new Locker());
		}
		lockers.get(key).lock();
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("lockers", lockers);
		return builder.toString();
	}
}
