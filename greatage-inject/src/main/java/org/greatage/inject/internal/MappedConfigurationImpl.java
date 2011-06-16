/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.inject.internal;

import org.greatage.inject.MappedConfiguration;
import org.greatage.inject.services.ServiceResources;
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
public class MappedConfigurationImpl<T, K, V> extends AbstractConfiguration<T, Map<K, V>>
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
