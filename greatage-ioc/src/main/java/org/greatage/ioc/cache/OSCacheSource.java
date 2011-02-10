package org.greatage.ioc.cache;

import org.greatage.util.DescriptionBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class OSCacheSource extends AbstractCacheSource {
	private final boolean useMemoryCaching;
	private final boolean unlimitedDiskCache;
	private final boolean overflowPersistence;

	public OSCacheSource(final boolean useMemoryCaching, final boolean unlimitedDiskCache, final boolean overflowPersistence) {
		this.useMemoryCaching = useMemoryCaching;
		this.unlimitedDiskCache = unlimitedDiskCache;
		this.overflowPersistence = overflowPersistence;
	}

	public <K, V> Cache<K, V> getCache(final String name) {
		final com.opensymphony.oscache.base.Cache cache =
				new com.opensymphony.oscache.base.Cache(useMemoryCaching, unlimitedDiskCache, overflowPersistence);
		return new OSCache<K, V>(cache, name);
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("useMemoryCaching", useMemoryCaching);
		builder.append("unlimitedDiskCache", unlimitedDiskCache);
		builder.append("overflowPersistence", overflowPersistence);
		return builder.toString();
	}
}
