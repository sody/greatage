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
