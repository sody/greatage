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

package org.greatage.ioc.inject;

import org.greatage.ioc.Configuration;
import org.greatage.ioc.ServiceResources;
import org.greatage.util.CollectionUtils;

import java.util.Collection;

/**
 * This class represents default implementation of unordered service configuration. The resulting type of such
 * configuration is {@link Collection}.
 *
 * @param <T> service type
 * @param <V> type of configuration items
 * @author Ivan Khalopik
 * @since 1.1
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
