/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
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
