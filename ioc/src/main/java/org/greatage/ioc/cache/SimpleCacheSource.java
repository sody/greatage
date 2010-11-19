/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleCacheSource implements CacheSource {

	public <K, V> Cache<K, V> getCache(final Class<K> keyClass, final Class<V> valueClass) {
		return new SimpleCache<K, V>();
	}
}
