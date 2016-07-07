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

import org.greatage.inject.Configuration;
import org.greatage.inject.Marker;
import org.greatage.inject.services.Injector;
import org.greatage.inject.services.ServiceResources;
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
public class ConfigurationImpl<T, V> extends AbstractConfiguration<T, Collection<V>> implements Configuration<V> {
	private final Collection<V> configuration = CollectionUtils.newSet();

	/**
	 * Creates new instance of unordered service configuration with defined service resources.
	 *
	 * @param resources service resource
	 */
	ConfigurationImpl(final Injector injector, final Marker<T> marker) {
		super(injector, marker);
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
