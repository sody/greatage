/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class EhcacheCache<K, V> implements Cache<K, V> {
	private final Ehcache ehcache;
	private final boolean serialized;

	public EhcacheCache(final Ehcache ehcache, final boolean serialized) {
		this.ehcache = ehcache;
		this.serialized = serialized;
	}

	public String getName() {
		return ehcache.getName();
	}

	public boolean contains(final K key) {
		return ehcache.get(key) != null;
	}

	@SuppressWarnings({"unchecked"})
	public V get(final K key) {
		final Element element = ehcache.get(key);
		return element != null ? (V) (serialized ? element.getValue() : element.getObjectValue()) : null;
	}

	public void put(final K key, final V value) {
		final Element element = new Element(key, value);
		ehcache.put(element);
	}

	public V remove(final K key) {
		final V value = get(key);
		ehcache.remove(key);
		return value;
	}

	public void clear() {
		ehcache.removeAll();
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("serialized", serialized);
		builder.append("ehcache", ehcache);
		return builder.toString();
	}
}
