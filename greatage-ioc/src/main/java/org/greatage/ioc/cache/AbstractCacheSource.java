/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represents abstract cache source implementation that delegates creation of cache by class to creation by
 * class name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractCacheSource implements CacheSource {

	/**
	 * {@inheritDoc} Delegates invocation to {@link #getCache(String)} method.
	 */
	public <K, V> Cache<K, V> getCache(final Class clazz) {
		return getCache(clazz.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
