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

import com.opensymphony.oscache.base.NeedsRefreshException;
import org.greatage.util.DescriptionBuilder;

/**
 * This class represents cache implementation for OSCache cache API.
 *
 * @param <K> type of cache keys
 * @param <V> type of cache items
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OSCache<K, V> implements Cache<K, V> {
	private static final int DEFAULT_REFRESH_PERIOD = 10000;

	private final com.opensymphony.oscache.base.Cache cache;
	private final String name;
	private final int refreshPeriod;
	private final String[] group;

	/**
	 * Creates new instance of cache implementation for OSCache cache API.
	 *
	 * @param cache OS cache instance
	 * @param name  cache name
	 */
	public OSCache(final com.opensymphony.oscache.base.Cache cache, final String name) {
		this(cache, name, DEFAULT_REFRESH_PERIOD);
	}

	/**
	 * Creates new instance of cache implementation for OSCache cache API.
	 *
	 * @param cache		 OS cache instance
	 * @param name		  cache name
	 * @param refreshPeriod How long before the object needs refresh. To allow the object to stay in the cache
	 *                      indefinitely, supply a value of CacheEntry.INDEFINITE_EXPIRY
	 */
	public OSCache(final com.opensymphony.oscache.base.Cache cache, final String name, final int refreshPeriod) {
		this.cache = cache;
		this.name = name;
		this.refreshPeriod = refreshPeriod;

		group = new String[]{name};
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(final K key) {
		return get(key) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public V get(final K key) {
		try {
			return (V) cache.getFromCache(toString(key), refreshPeriod);
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(toString(key));
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void put(final K key, final V value) {
		cache.putInCache(toString(key), value, group);
	}

	/**
	 * {@inheritDoc}
	 */
	public V remove(final K key) {
		final V result = get(key);
		cache.flushEntry(toString(key));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		cache.flushGroup(name);
	}

	/**
	 * Gets unique string representation of cache key. It adds cache name to make resulting key more unique.
	 *
	 * @param key cache key
	 * @return unique value of cache key that can be used as global cache key
	 */
	private String toString(final K key) {
		return String.valueOf(key) + "." + name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("name", name);
		builder.append("cache", cache);
		return builder.toString();
	}
}
