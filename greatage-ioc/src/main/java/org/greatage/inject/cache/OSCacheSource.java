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

package org.greatage.inject.cache;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represents cache source implementation for OSCache cache API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OSCacheSource extends AbstractCacheSource {
	private final boolean useMemoryCaching;
	private final boolean unlimitedDiskCache;
	private final boolean overflowPersistence;

	/**
	 * Creates new instance of cache source for OSCache cache API.
	 *
	 * @param useMemoryCaching	determines if the memory caching is going to be used
	 * @param unlimitedDiskCache  determines if the disk caching is unlimited
	 * @param overflowPersistence determines if the persistent cache is used in overflow only mode
	 */
	public OSCacheSource(final boolean useMemoryCaching,
						 final boolean unlimitedDiskCache,
						 final boolean overflowPersistence) {
		this.useMemoryCaching = useMemoryCaching;
		this.unlimitedDiskCache = unlimitedDiskCache;
		this.overflowPersistence = overflowPersistence;
	}

	/**
	 * {@inheritDoc}
	 */
	public <K, V> Cache<K, V> getCache(final String name) {
		final com.opensymphony.oscache.base.Cache cache =
				new com.opensymphony.oscache.base.Cache(useMemoryCaching, unlimitedDiskCache, overflowPersistence);
		return new OSCache<K, V>(cache, name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("useMemoryCaching", useMemoryCaching);
		builder.append("unlimitedDiskCache", unlimitedDiskCache);
		builder.append("overflowPersistence", overflowPersistence);
		return builder.toString();
	}
}
