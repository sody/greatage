/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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

package org.greatage.inject.cache;

import org.greatage.util.CollectionUtils;
import org.greatage.util.DescriptionBuilder;

import java.util.Map;

/**
 * This class represents simple cache implementation.
 *
 * @param <K> type of cache keys
 * @param <V> type of cache items
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleCache<K, V> implements Cache<K, V> {
	private static final int DEFAULT_TIME_TO_LIVE = 120000;
	private static final int DEFAULT_READS_TO_LIVE = 50;

	private final Map<K, SimpleCacheElement<V>> cache = CollectionUtils.newMap();
	private final String name;
	private final int timeToLive;
	private final int readsToLive;

	/**
	 * Creates new instance of simple cache.
	 *
	 * @param name cache name
	 */
	public SimpleCache(final String name) {
		this(name, DEFAULT_TIME_TO_LIVE, DEFAULT_READS_TO_LIVE);
	}

	/**
	 * Creates new instance of simple cache.
	 *
	 * @param name		cache name
	 * @param timeToLive  time before cache item will be marked as expired and then deleted
	 * @param readsToLive number of reads before cache item will be marked as expired and then deleted
	 */
	public SimpleCache(final String name, final int timeToLive, final int readsToLive) {
		this.name = name;
		this.timeToLive = timeToLive;
		this.readsToLive = readsToLive;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(final K key) {
		final SimpleCacheElement<V> cacheElement = cache.get(key);
		return cacheElement != null && !cacheElement.isExpired();
	}

	/**
	 * {@inheritDoc}
	 */
	public V get(final K key) {
		final SimpleCacheElement<V> cacheElement = cache.get(key);
		if (cacheElement != null && cacheElement.isExpired()) {
			cache.remove(key);
		}
		return cacheElement != null ? cacheElement.getValue() : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void put(final K key, final V value) {
		final SimpleCacheElement<V> cacheElement = new SimpleCacheElement<V>(value, timeToLive, readsToLive);
		cache.put(key, cacheElement);
	}

	/**
	 * {@inheritDoc}
	 */
	public V remove(final K key) {
		final SimpleCacheElement<V> cacheElement = cache.remove(key);
		return cacheElement != null ? cacheElement.getValue() : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		cache.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("timeToLive", timeToLive);
		builder.append("readsToLive", readsToLive);
		return builder.toString();
	}
}
