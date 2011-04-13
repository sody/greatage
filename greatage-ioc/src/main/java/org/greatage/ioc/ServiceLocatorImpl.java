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

package org.greatage.ioc;

import org.greatage.ioc.inject.Injector;
import org.greatage.ioc.logging.Logger;
import org.greatage.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents default {@link ServiceLocator} implementation that is used as main entry point of Great Age IoC
 * container.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceLocatorImpl implements ServiceLocator {
	private final Map<Marker<?>, Object> services = CollectionUtils.newConcurrentMap();
	private final Map<Marker<?>, String> scopes = CollectionUtils.newConcurrentMap();
	private final Logger logger;

	/**
	 * Creates new instance of service locator with defined modules.
	 *
	 * @param modules  modules
	 * @param injector injector
	 */
	public ServiceLocatorImpl(final Collection<Module> modules, final Logger logger, final Injector injector) {
		assert modules != null;
		assert logger != null;
		assert injector != null;

		this.logger = logger;
		//creating and overriding service definitions
		//TODO: implement this using set
		final Map<Marker<?>, ServiceDefinition<?>> internalServices = CollectionUtils.newMap();
		for (Module module : modules) {
			for (ServiceDefinition<?> service : module.getDefinitions()) {
				final Marker<?> marker = service.getMarker();
				if (!service.isOverride() && internalServices.containsKey(marker)) {
					throw new ApplicationException(String.format("Service (%s) already declared", marker));
				}
				internalServices.put(marker, service);
			}
		}

		for (ServiceDefinition<?> service : internalServices.values()) {
			addService(injector, service, modules);
		}

		//logging statistics
		logStatistics();
	}

	public Set<Marker<?>> getMarkers() {
		return services.keySet();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final Class<T> serviceClass) {
		return getService(Marker.get(serviceClass));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final Marker<T> marker) {
		final Set<T> services = findServices(marker);
		if (services.size() > 1) {
			throw new ApplicationException(String.format("Can't find service (%s). More than one service available", marker));
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
		return findServices(Marker.get(serviceClass));
	}

	public <T> Set<T> findServices(final Marker<T> marker) {
		final Set<T> result = CollectionUtils.newSet();
		for (Map.Entry<Marker<?>, ?> entry : services.entrySet()) {
			if (marker.isAssignableFrom(entry.getKey())) {
				final Object service = entry.getValue();
				result.add(marker.getServiceClass().cast(service));
			}
		}
		return result;
	}

	private <T> void addService(final Injector injector,
								final ServiceDefinition<T> service,
								final Collection<Module> modules) {
		final Marker<T> marker = service.getMarker();
		final List<ServiceContributor<T>> contributors = CollectionUtils.newList();
		final List<ServiceDecorator<T>> decorators = CollectionUtils.newList();
		for (Module module : modules) {
			contributors.addAll(module.getContributors(marker));
			decorators.addAll(module.getDecorators(marker));
		}

		final T serviceInstance = injector.createService(service, contributors, decorators);
		services.put(marker, serviceInstance);
		scopes.put(marker, service.getScope());
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
		for (Marker<?> marker : services.keySet()) {
			final String name = marker.getServiceClass().getSimpleName();
			final String scope = scopes.get(marker);
			statistics.append(String.format(format, name, scope));
		}
		logger.info(statistics.toString());
	}
}
