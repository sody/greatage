package org.greatage.ioc.cache;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represents cache implementation for JSR-107 cache API.
 *
 * @param <K> type of cache keys
 * @param <V> type of cache items
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JCache<K, V> implements Cache<K, V> {
	private final net.sf.jsr107cache.Cache cache;
	private final String name;

	/**
	 * Creates new instance of cache implementation for JSR-107 cache API.
	 *
	 * @param cache JSR-107 cache instance
	 * @param name  cache name
	 */
	public JCache(final net.sf.jsr107cache.Cache cache, final String name) {
		this.cache = cache;
		this.name = name;
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
		return cache.containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public V get(final K key) {
		return (V) cache.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public void put(final K key, final V value) {
		cache.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public V remove(final K key) {
		return (V) cache.remove(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		cache.clear();
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
