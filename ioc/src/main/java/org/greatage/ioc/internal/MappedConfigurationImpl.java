/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal;

import org.greatage.ioc.MappedConfiguration;
import org.greatage.ioc.ServiceResources;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MappedConfigurationImpl<T, K, V> extends AbstractConfiguration<T, V, Map<K, V>>
		implements MappedConfiguration<K, V> {
	private final Map<K, V> configuration = CollectionUtils.newMap();

	MappedConfigurationImpl(final ServiceResources<T> resources) {
		super(resources);
	}

	public MappedConfiguration<K, V> add(final K key, final V value) {
		configuration.put(key, value);
		return this;
	}

	public MappedConfiguration<K, V> addInstance(final K key, final Class<? extends V> valueClass) {
		add(key, newInstance(valueClass));
		return this;
	}

	public Map<K, V> build() {
		return configuration;
	}
}
