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

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.greatage.util.DescriptionBuilder;

/**
 * This class represents cache implementation for Ehcache cache API.
 *
 * @param <K> type of cache keys
 * @param <V> type of cache items
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EhcacheCache<K, V> implements Cache<K, V> {
	private final Ehcache ehcache;
	private final boolean serialized;

	/**
	 * Creates new instance of cache implementation for Ehcache cache API.
	 *
	 * @param ehcache	ehcache cache instance
	 * @param serialized option that determines is cache items can be serialized and saved to external storage
	 */
	public EhcacheCache(final Ehcache ehcache, final boolean serialized) {
		this.ehcache = ehcache;
		this.serialized = serialized;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return ehcache.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(final K key) {
		return ehcache.get(key) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public V get(final K key) {
		final Element element = ehcache.get(key);
		return element != null ? (V) (serialized ? element.getValue() : element.getObjectValue()) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void put(final K key, final V value) {
		final Element element = new Element(key, value);
		ehcache.put(element);
	}

	/**
	 * {@inheritDoc}
	 */
	public V remove(final K key) {
		final V value = get(key);
		ehcache.remove(key);
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		ehcache.removeAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("serialized", serialized);
		builder.append("ehcache", ehcache);
		return builder.toString();
	}
}
