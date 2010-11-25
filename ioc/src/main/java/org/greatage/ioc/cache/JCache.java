package org.greatage.ioc.cache;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JCache<K, V> implements Cache<K, V> {
	private final net.sf.jsr107cache.Cache cache;
	private final String name;

	public JCache(final net.sf.jsr107cache.Cache cache, final String name) {
		this.cache = cache;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean contains(final K key) {
		return cache.containsKey(key);
	}

	@SuppressWarnings({"unchecked"})
	public V get(final K key) {
		return (V) cache.get(key);
	}

	public void put(final K key, final V value) {
		cache.put(key, value);
	}

	@SuppressWarnings({"unchecked"})
	public V remove(final K key) {
		return (V) cache.remove(key);
	}

	public void clear() {
		cache.clear();
	}
}
