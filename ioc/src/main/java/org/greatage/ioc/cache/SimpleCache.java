/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import org.greatage.util.CollectionUtils;
import org.greatage.util.DescriptionBuilder;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleCache<K, V> implements Cache<K, V> {
	private static final int DEFAULT_TIME_TO_LIVE = 120000;
	private static final int DEFAULT_READS_TO_LIVE = 50;

	private final Map<K, SimpleCacheElement<V>> cache = CollectionUtils.newMap();
	private final int timeToLive;
	private final int readsToLive;

	public SimpleCache() {
		this(DEFAULT_TIME_TO_LIVE, DEFAULT_READS_TO_LIVE);
	}

	public SimpleCache(final int timeToLive, final int readsToLive) {
		this.timeToLive = timeToLive;
		this.readsToLive = readsToLive;
	}

	public boolean contains(final K key) {
		final SimpleCacheElement<V> cacheElement = cache.get(key);
		return cacheElement != null && !cacheElement.isExpired();
	}

	public V get(final K key) {
		final SimpleCacheElement<V> cacheElement = cache.get(key);
		if (cacheElement != null && cacheElement.isExpired()) {
			cache.remove(key);
		}
		return cacheElement != null ? cacheElement.getValue() : null;
	}

	public void put(final K key, final V value) {
		final SimpleCacheElement<V> cacheElement = new SimpleCacheElement<V>(value, timeToLive, readsToLive);
		cache.put(key, cacheElement);
	}

	public V remove(final K key) {
		final SimpleCacheElement<V> cacheElement = cache.remove(key);
		return cacheElement != null ? cacheElement.getValue() : null;
	}

	public void clear() {
		cache.clear();
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("timeToLive", timeToLive);
		builder.append("readsToLive", readsToLive);
		return builder.toString();
	}
}
