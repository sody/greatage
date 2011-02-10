/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractCacheSource implements CacheSource {

	public <K, V> Cache<K, V> getCache(final Class clazz) {
		return getCache(clazz.getName());
	}

	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
