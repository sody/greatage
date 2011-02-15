/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.greatage.util.DescriptionBuilder;

/**
 * This class represents cache source implementation for Ehcache cache API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EhcacheCacheSource extends AbstractCacheSource {
	private final CacheManager cacheManager;

	/**
	 * Creates new instance of cache source for Ehcache cache API.
	 *
	 * @param cacheManager Ehcache cache manager
	 */
	public EhcacheCacheSource(final CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * {@inheritDoc}
	 */
	public <K, V> Cache<K, V> getCache(final String name) {
		final Ehcache ehcache = cacheManager.addCacheIfAbsent(name);
		return new EhcacheCache<K, V>(ehcache, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("manager", cacheManager);
		return builder.toString();
	}
}
