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

import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceContributor;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.logging.Logger;
import org.greatage.util.Locker;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class represents service resources proxy that is used during service build phase and adds resulting service configuration
 * as additional resource. This resources are calculated with service contributors during configure phase.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class BuildResources<T> implements ServiceResources<T> {
	private final Logger logger;
	private final ServiceResources<T> resources;
	private final List<ServiceContributor<T>> contributors;

	private final Locker locker = new Locker();

	/**
	 * Creates new instance of service resources proxy for build phase with defined service resources delegate and list of service
	 * contributors.
	 *
	 * @param resources	service resources
	 * @param contributors service contributors
	 */
	public BuildResources(final Logger logger,
						  final ServiceResources<T> resources,
						  final List<ServiceContributor<T>> contributors) {
		this.logger = logger;
		this.resources = resources;
		this.contributors = contributors;
	}

	public Marker<T> getMarker() {
		return resources.getMarker();
	}

	public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
		if (Map.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getMappedConfiguration());
		}
		if (List.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getOrderedConfiguration());
		}
		if (Collection.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getConfiguration());
		}
		return resources.getResource(resourceClass, annotations);
	}

	/**
	 * Creates mapped service configuration contributed by all configured service contributors.
	 *
	 * @param <K> type of configuration keys
	 * @param <V> type of configuration values
	 * @return map as service mapped configuration, not null
	 */
	private <K, V> Map<K, V> getMappedConfiguration() {
		final MappedConfigurationImpl<T, K, V> configuration = new MappedConfigurationImpl<T, K, V>(resources);
		configure(configuration);
		return configuration.build();
	}

	/**
	 * Creates ordered service configuration contributed by all configured service contributors.
	 *
	 * @param <V> type of configuration items
	 * @return list as service ordered configuration, not null
	 */
	private <V> List<V> getOrderedConfiguration() {
		final OrderedConfigurationImpl<T, V> configuration = new OrderedConfigurationImpl<T, V>(resources);
		configure(configuration);
		return configuration.build();
	}

	/**
	 * Creates unordered service configuration contributed by all configured service contributors.
	 *
	 * @param <V> type of configuration items
	 * @return collection as service unordered configuration, not null
	 */
	private <V> Collection<V> getConfiguration() {
		final ConfigurationImpl<T, V> configuration = new ConfigurationImpl<T, V>(resources);
		configure(configuration);
		return configuration.build();
	}

	/**
	 * Contributes service configuration by triggering configure phase.
	 *
	 * @param configuration correspondent service configuration instance
	 */
	private void configure(final Object configuration) {
		final ServiceResources<T> extraResources = new ExtraResources<T>(resources, configuration);
		for (ServiceContributor<T> contributor : contributors) {
			logger.debug("Configuring service (%s) from (%s)", resources.getMarker(), contributor);
			contributor.contribute(extraResources);
		}
	}
}
