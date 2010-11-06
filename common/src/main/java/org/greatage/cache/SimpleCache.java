/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.cache;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleCache<K, V> implements Cache<K, V> {
	private static final int DEFAULT_TIME_TO_LIVE = 120000;
	private static final int DEFAULT_READS_TO_LIVE = 50;

	private final Map<K, CacheElement<V>> cache = CollectionUtils.newMap();
	private final int timeToLive;
	private final int readsToLive;

	public SimpleCache() {
		this(DEFAULT_TIME_TO_LIVE, DEFAULT_READS_TO_LIVE);
	}

	public SimpleCache(int timeToLive, int readsToLive) {
		this.timeToLive = timeToLive;
		this.readsToLive = readsToLive;
	}

	public V get(K key) {
		final CacheElement<V> cacheElement = cache.get(key);
		if (cacheElement != null && cacheElement.isExpired()) {
			cache.remove(key);
		}
		return cacheElement != null ? cacheElement.getValue() : null;
	}

	public void put(K key, V value) {
		final CacheElement<V> cacheElement = new CacheElement<V>(value, timeToLive, readsToLive);
		cache.put(key, cacheElement);
	}

	public boolean contains(K key) {
		final CacheElement<V> cacheElement = cache.get(key);
		return cacheElement != null && !cacheElement.isExpired();
	}

	public void invalidate() {
		cache.clear();
	}
}
