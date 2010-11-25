package org.greatage.ioc.cache;

import com.opensymphony.oscache.base.NeedsRefreshException;
import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OSCache<K, V> implements Cache<K, V> {
	private static final int DEFAULT_REFRESH_PERIOD = 10000;

	private final com.opensymphony.oscache.base.Cache cache;
	private final String name;
	private final int refreshPeriod;
	private final String[] group;

	public OSCache(final com.opensymphony.oscache.base.Cache cache, final String name) {
		this(cache, name, DEFAULT_REFRESH_PERIOD);
	}

	public OSCache(final com.opensymphony.oscache.base.Cache cache, final String name, final int refreshPeriod) {
		this.cache = cache;
		this.name = name;
		this.refreshPeriod = refreshPeriod;

		group = new String[]{name};
	}

	public String getName() {
		return name;
	}

	public boolean contains(final K key) {
		return get(key) != null;
	}

	@SuppressWarnings({"unchecked"})
	public V get(final K key) {
		try {
			return (V) cache.getFromCache(toString(key), refreshPeriod);
		}
		catch (NeedsRefreshException e) {
			cache.cancelUpdate(toString(key));
			return null;
		}
	}

	public void put(final K key, final V value) {
		cache.putInCache(toString(key), value, group);
	}

	public V remove(final K key) {
		final V result = get(key);
		cache.flushEntry(toString(key));
		return result;
	}

	public void clear() {
		cache.flushGroup(name);
	}

	private String toString(final K key) {
		return String.valueOf(key) + "." + name;
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("name", name);
		builder.append("cache", cache);
		return builder.toString();
	}
}
