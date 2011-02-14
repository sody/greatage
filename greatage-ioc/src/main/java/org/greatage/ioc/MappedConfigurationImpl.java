/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents default implementation of mapped service configuration. The resulting type of such
 * configuration is {@link Map}.
 *
 * @param <T> service type
 * @param <K> type of configuration keys
 * @param <V> type of configuration values
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MappedConfigurationImpl<T, K, V> extends AbstractConfiguration<T, V, Map<K, V>>
		implements MappedConfiguration<K, V> {
	private final Map<K, V> configuration = CollectionUtils.newMap();

	/**
	 * Creates new instance of mapped service configuration with defined service resources.
	 *
	 * @param resources service resource
	 */
	MappedConfigurationImpl(final ServiceResources<T> resources) {
		super(resources);
	}

	/**
	 * {@inheritDoc}
	 */
	public MappedConfiguration<K, V> add(final K key, final V value) {
		configuration.put(key, value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public MappedConfiguration<K, V> addInstance(final K key, final Class<? extends V> valueClass) {
		add(key, newInstance(valueClass));
		return this;
	}

	/**
	 * Creates resulting instance of mapped service configuration as map of configuration items.
	 *
	 * @return resulting map of configuration items
	 */
	public Map<K, V> build() {
		return configuration;
	}
}
