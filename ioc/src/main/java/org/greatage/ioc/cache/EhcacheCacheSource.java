/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EhcacheCacheSource extends AbstractCacheSource {
	private final CacheManager cacheManager;

	public EhcacheCacheSource(final CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public <K, V> Cache<K, V> getCache(final String name) {
		final Ehcache ehcache = cacheManager.addCacheIfAbsent(name);

		return new EhcacheCache<K, V>(ehcache, true);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("manager", cacheManager);
		return builder.toString();
	}
}
