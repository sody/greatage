package org.greatage.ioc.cache;

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
