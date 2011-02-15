/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

/**
 * This class represents simple cache source implementation.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleCacheSource extends AbstractCacheSource {

	/**
	 * {@inheritDoc}
	 */
	public <K, V> Cache<K, V> getCache(final String name) {
		return new SimpleCache<K, V>(name);
	}
}
