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

import org.greatage.inject.ApplicationException;
import org.greatage.inject.Key;
import org.greatage.inject.Marker;
import org.greatage.inject.ServiceLocator;
import org.greatage.inject.services.Injector;
import org.greatage.inject.services.Module;
import org.greatage.inject.services.ScopeManager;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents default {@link org.greatage.inject.ServiceLocator} implementation that is used as main entry
 * point of Great Age IoC container.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorImpl implements ServiceLocator {
	private final Logger logger = LoggerFactory.getLogger(ServiceLocatorImpl.class);

	private final ScopeManager scopeManager;

	/**
	 * Creates new instance of service locator with defined modules.
	 *
	 * @param modules  modules
	 * @param injector injector
	 */
	public ServiceLocatorImpl(final Collection<Module> modules,
							  final Injector injector,
							  final ScopeManager scopeManager) {
		assert modules != null;
		assert injector != null;
		assert scopeManager != null;

		this.scopeManager = scopeManager;

		//creating and overriding service definitions
		//TODO: implement this using set
		final List<Marker<?>> markers = CollectionUtils.newList();
		final Map<Marker<?>, ServiceDefinition<?>> definitions = CollectionUtils.newMap();
		for (Module module : modules) {
			for (ServiceDefinition<?> definition : module.getDefinitions()) {
				final Marker<?> marker = definition.getMarker();
				if (definitions.containsKey(marker)) {
					if (!definition.isOverride()) {
						throw new ApplicationException(String.format("Service (%s) already declared", marker));
					}
					markers.remove(marker);
				}
				markers.add(marker);
				definitions.put(marker, definition);
			}
		}

		for (Marker<?> marker : markers) {
			final ServiceDefinition<?> definition = definitions.get(marker);
			addService(injector, definition, modules);
		}

		//logging statistics
		logStatistics();
	}

	public Set<Marker<?>> getMarkers() {
		return scopeManager.getMarkers();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final Class<T> serviceClass) {
		return getService(Key.get(serviceClass));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final Marker<T> marker) {
		final Set<T> services = scopeManager.find(marker);
		if (services.size() > 1) {
			throw new ApplicationException(
					String.format("Can't find service (%s). More than one service available", marker));
		}
		if (services.size() < 1) {
			throw new ApplicationException(String.format("Can't find service (%s)", marker));
		}
		return services.iterator().next();
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> Set<T> findServices(final Class<T> serviceClass) {
		return findServices(Key.get(serviceClass));
	}

	public <T> Set<T> findServices(final Marker<T> marker) {
		return scopeManager.find(marker);
	}

	private <T> void addService(final Injector injector,
								final ServiceDefinition<T> service,
								final Collection<Module> modules) {
		final ServiceBuilderImpl<T> builder = new ServiceBuilderImpl<T>(injector, service);
		for (Module module : modules) {
			builder.addContributors(module);
			builder.addInterceptors(module);
		}
		scopeManager.register(builder);
	}

	private void logStatistics() {
		int maxLength = 0;
		for (Marker<?> marker : getMarkers()) {
			final String name = marker.getServiceClass().getSimpleName();
			if (name.length() > maxLength) {
				maxLength = name.length();
			}
		}

		final StringBuilder statistics = new StringBuilder("Statistics:\n");
		final String format = "%" + maxLength + "s : [%s]\n";
		for (Marker<?> marker : getMarkers()) {
			final String name = marker.getServiceClass().getSimpleName();
			final String scope = marker.getScope() != null ? marker.getScope().getSimpleName() : "Default";
			statistics.append(String.format(format, name, scope));
		}
		logger.info(statistics.toString());
	}
}
