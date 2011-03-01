/*
 * Copyright 2011 Ivan Khalopik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
