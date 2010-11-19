/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.greatage.util.DescriptionBuilder;

import java.io.Serializable;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EhcacheCacheSource implements CacheSource {
	private final CacheManager cacheManager;

	public EhcacheCacheSource(final CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public <K, V> Cache<K, V> getCache(final Class<K> keyClass, final Class<V> valueClass) {
		final boolean serialized = Serializable.class.isAssignableFrom(keyClass) &&
				Serializable.class.isAssignableFrom(valueClass);

		final String cacheName = keyClass.getSimpleName() + valueClass.getSimpleName();
		final Ehcache ehcache = cacheManager.addCacheIfAbsent(cacheName);

		return new EhcacheCache<K, V>(ehcache, serialized);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("manager", cacheManager);
		return builder.toString();
	}
}
