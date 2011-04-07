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
import org.greatage.ioc.inject.InternalInjector;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeConstants;
import org.greatage.ioc.scope.ScopeManager;
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
public class ServiceLocatorImpl implements ServiceLocator, ServiceDefinition<ServiceLocator> {
	private final Map<Marker<?>, Scope> servicesById = CollectionUtils.newConcurrentMap();

	private final Marker<ServiceLocator> marker;
	private final Logger logger;

	/**
	 * Creates new instance of service locator with defined modules.
	 *
	 * @param logger  system logger
	 * @param modules modules
	 */
	ServiceLocatorImpl(final Logger logger, final List<Module> modules) {
		this.logger = logger;
		marker = Marker.generate(ServiceLocator.class);

		//creating and overriding service definitions
		//TODO: implement this using set
		final Map<Marker<?>, ServiceDefinition<?>> services = CollectionUtils.newMap();
		final Map<Marker<?>, ServiceDefinition<?>> internalServices = CollectionUtils.newMap();
		for (Module module : modules) {
			for (ServiceDefinition<?> service : module.getDefinitions()) {
				final Marker<?> marker = service.getMarker();
				if (!service.isOverride() && (services.containsKey(marker) || internalServices.containsKey(marker))) {
					throw new ApplicationException(String.format("Service (%s) already declared", marker));
				}
				if (ScopeConstants.INTERNAL.equals(service.getScope())) {
					internalServices.put(marker, service);
				}
				else {
					services.put(marker, service);
				}
			}
		}

		//adding service locator as internal service
		//internal services use separate sandbox injector to prevent cyclic dependency injection
		final Injector internalInjector = new InternalInjector();
		addService(internalInjector, this, modules);

		//initializing internal services
		for (ServiceDefinition<?> service : internalServices.values()) {
			addService(internalInjector, service, modules);
		}

		//initializing services
		final Injector injector = internalInjector.inject(marker, Injector.class);
		for (ServiceDefinition<?> service : services.values()) {
			addService(injector, service, modules);
		}

		//logging statistics
		logStatistics();
	}

	public Marker<ServiceLocator> getMarker() {
		return marker;
	}

	public String getScope() {
		return ScopeConstants.INTERNAL;
	}

	public boolean isOverride() {
		return false;
	}

	public ServiceLocator build(final ServiceResources<ServiceLocator> serviceLocatorServiceResources) {
		return this;
	}

	public Set<Marker<?>> getMarkers() {
		return servicesById.keySet();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final Class<T> serviceClass) {
		return getService(Marker.generate(serviceClass));
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
		return findServices(Marker.generate(serviceClass));
	}

	public <T> Set<T> findServices(final Marker<T> marker) {
		final Set<T> result = CollectionUtils.newSet();
		for (Map.Entry<Marker<?>, Scope> entry : servicesById.entrySet()) {
			if (marker.isAssignableFrom(entry.getKey())) {
				final Object service = entry.getValue().get(entry.getKey());
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
		final ObjectBuilder<T> builder = new ServiceBuilder<T>(injector, service, contributors, decorators);

		final ScopeManager scopeManager = injector.inject(service.getMarker(), ScopeManager.class);
		final Scope scope = scopeManager.getScope(service.getScope());
		scope.put(marker, builder);

		servicesById.put(marker, scope);
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
		final String format = "%" + maxLength + "s : [%s] %s\n";
		for (Map.Entry<Marker<?>, Scope> entry : servicesById.entrySet()) {
			final String name = entry.getKey().getServiceClass().getSimpleName();
			final String scope = entry.getValue().getName();
			final String implementation = entry.getKey().getTargetClass().getName();
			statistics.append(String.format(format, name, scope, implementation));
		}
		logger.info(statistics.toString());
	}
}
