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

import org.greatage.ioc.inject.ServiceBuilder;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.logging.Slf4jLogger;
import org.greatage.ioc.proxy.JdkProxyFactory;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.scope.GlobalScope;
import org.greatage.ioc.scope.Scope;
import org.greatage.util.CollectionUtils;
import org.greatage.util.Locker;
import org.greatage.util.OrderingUtils;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class represents utility that simplifies {@link ServiceLocator} building process.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceLocatorBuilder {
	private final ServiceLocatorModule rootModule;
	private final Locker locker = new Locker();

	private Scope scope;
	private ProxyFactory proxyFactory;
	private final Collection<Module> modules = CollectionUtils.newList();
	private final Map<Marker, ServiceDefinition> services = CollectionUtils.newMap();
	private final Map<Marker, Object> cache = CollectionUtils.newMap();

	/**
	 * Creates new service locator builder with defined {@link IOCModule} core module. It will use console logger for system logs.
	 */
	public ServiceLocatorBuilder() {
		this(new Slf4jLogger(LoggerFactory.getLogger(ServiceLocator.class)));
	}

	/**
	 * Creates new service locator builder with defined {@link IOCModule} core module and system logger.
	 *
	 * @param logger system logger
	 */
	public ServiceLocatorBuilder(final Logger logger) {
		rootModule = new ServiceLocatorModule(logger);
	}

	/**
	 * Adds module instances to service locator builder.
	 *
	 * @param moduleInstances module instances
	 * @return this instance
	 */
	public ServiceLocatorBuilder addModules(final Module... moduleInstances) {
		locker.check();
		rootModule.addModules(moduleInstances);
		return this;
	}

	/**
	 * Adds module classes to service locator builder.
	 *
	 * @param moduleClasses module classes
	 * @return this instance
	 */
	public ServiceLocatorBuilder addModules(final Class<?>... moduleClasses) {
		locker.check();
		rootModule.addModules(moduleClasses);
		return this;
	}

	/**
	 * Adds module instance to service locator builder.
	 *
	 * @param moduleInstance module instance
	 * @return this instance
	 */
	public ServiceLocatorBuilder addModule(final Module moduleInstance) {
		locker.check();
		rootModule.addModules(moduleInstance);
		return this;
	}

	/**
	 * Adds module class to service locator builder.
	 *
	 * @param <T>         module type
	 * @param moduleClass module class
	 * @return this instance
	 */
	public <T> ServiceLocatorBuilder addModule(final Class<T> moduleClass) {
		locker.check();
		rootModule.addModules(moduleClass);
		return this;
	}

	/**
	 * Builds new service locator instance with all collected modules defined.
	 *
	 * @return new service locator instance
	 */
	public ServiceLocator build() {
		locker.lock();
		scope = new GlobalScope();
		proxyFactory = new JdkProxyFactory();
		modules.addAll(rootModule.getModules());

		for (Module module : modules) {
			for (ServiceDefinition<?> service : module.getDefinitions()) {
				final Marker<?> marker = service.getMarker();
				if (!service.isOverride() && services.containsKey(marker)) {
					throw new ApplicationException(String.format("Service (%s) already declared", marker));
				}
				services.put(marker, service);
			}
		}

		final Marker<ServiceLocator> marker = Marker.generate(ServiceLocator.class);
		final ServiceResources<ServiceLocator> resources = new InternalServiceResources<ServiceLocator>(marker);
		proxyFactory = resources.getResource(ProxyFactory.class);
		return resources.getResource(ServiceLocator.class);
	}

	public static ServiceLocator createServiceLocator(final Logger logger, final Module... modules) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder(logger).addModules(modules);
		return builder.build();
	}

	public static ServiceLocator createServiceLocator(final Module... modules) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder().addModules(modules);
		return builder.build();
	}

	/**
	 * Creates new service locator instance for specified module classes + IOCModule. It will use console logger for all system
	 * logs.
	 *
	 * @param moduleClasses module classes
	 * @return new service locator instance
	 */
	public static ServiceLocator createServiceLocator(final Class... moduleClasses) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder().addModules(moduleClasses);
		return builder.build();
	}

	/**
	 * Creates new service locator instance for specified module classes + IOCModule with defined system logger.
	 *
	 * @param logger		system logger
	 * @param moduleClasses module classes
	 * @return new service locator instance
	 */
	public static ServiceLocator createServiceLocator(final Logger logger, final Class... moduleClasses) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder(logger).addModules(moduleClasses);
		return builder.build();
	}

	class InternalServiceResources<T> implements ServiceResources<T> {
		private final Marker<T> marker;

		InternalServiceResources(final Marker<T> marker) {
			this.marker = marker;
		}

		public Marker<T> getMarker() {
			return marker;
		}

		public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
			if (Scope.class.equals(resourceClass)) {
				return resourceClass.cast(scope);
			}
			final Marker<R> resourceMarker = Marker.generate(resourceClass, annotations);
			if (!cache.containsKey(resourceMarker)) {
				final R service = createService(resourceMarker);
				cache.put(resourceMarker, service);
			}
			return resourceClass.cast(cache.get(resourceMarker));
		}

		public <R> R createService(final Marker<R> marker) {
			final ServiceDefinition<R> service = services.get(marker);
			final List<ServiceContributor<R>> contributors = CollectionUtils.newList();
			final List<ServiceDecorator<R>> decorators = CollectionUtils.newList();
			for (Module module : modules) {
				contributors.addAll(module.getContributors(marker));
				decorators.addAll(module.getDecorators(marker));
			}
			final List<ServiceContributor<R>> orderedContributors = OrderingUtils.order(contributors);
			final List<ServiceDecorator<R>> orderedDecorators = OrderingUtils.order(decorators);

			final InternalServiceResources<R> resources = new InternalServiceResources<R>(marker);

			final ServiceBuilder<R> builder =
					new ServiceBuilder<R>(service, orderedContributors, orderedDecorators, resources, scope);
			return proxyFactory.createProxy(builder);
		}
	}
}
