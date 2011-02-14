/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.CollectionUtils;

import java.util.Collection;

/**
 * This class represents default implementation of unordered service configuration. The resulting type of such
 * configuration is {@link Collection}.
 *
 * @param <T> service type
 * @param <V> type of configuration items
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ConfigurationImpl<T, V> extends AbstractConfiguration<T, V, Collection<V>> implements Configuration<V> {
	private final Collection<V> configuration = CollectionUtils.newSet();

	/**
	 * Creates new instance of unordered service configuration with defined service resources.
	 *
	 * @param resources service resource
	 */
	ConfigurationImpl(final ServiceResources<T> resources) {
		super(resources);
	}

	/**
	 * {@inheritDoc}
	 */
	public Configuration<V> add(final V item) {
		configuration.add(item);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public Configuration<V> addInstance(final Class<? extends V> clazz) {
		return add(newInstance(clazz));
	}

	/**
	 * Creates resulting instance of unordered service configuration as collection of configuration items.
	 *
	 * @return resulting collection of configuration items
	 */
	public Collection<V> build() {
		return configuration;
	}
}
