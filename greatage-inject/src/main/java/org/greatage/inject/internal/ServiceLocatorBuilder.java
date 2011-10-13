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
import org.greatage.inject.annotations.Singleton;
import org.greatage.inject.internal.proxy.JdkProxyFactory;
import org.greatage.inject.internal.scope.CachedBuilder;
import org.greatage.inject.services.Injector;
import org.greatage.inject.services.Module;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.ScopeManager;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.util.CollectionUtils;
import org.greatage.util.Locker;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * This class represents utility that simplifies {@link org.greatage.inject.ServiceLocator} building process.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorBuilder {
	private final ServiceLocatorModule rootModule = new ServiceLocatorModule();
	private final Locker locker = new Locker();

	public static ServiceLocator createServiceLocator(final Module... modules) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder().addModules(modules);
		return builder.build();
	}

	/**
	 * Creates new service locator instance for specified module classes + IOCModule. It will use console logger for all
	 * system logs.
	 *
	 * @param moduleClasses module classes
	 * @return new service locator instance
	 */
	public static ServiceLocator createServiceLocator(final Class... moduleClasses) {
		final ServiceLocatorBuilder builder = new ServiceLocatorBuilder().addModules(moduleClasses);
		return builder.build();
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
		final Injector injector = new InternalInjector(rootModule.getModules());
		return injector.inject(Key.get(ServiceLocator.class), ServiceLocator.class);
	}

	class InternalInjector implements Injector, ScopeManager {
		private final Map<Marker, Object> services = CollectionUtils.newMap();
		private final Map<Marker<?>, ServiceDefinition<?>> definitions = CollectionUtils.newMap();
		private final ProxyFactory proxyFactory = new JdkProxyFactory();

		private final Collection<Module> modules;

		InternalInjector(final Collection<Module> modules) {
			this.modules = modules;

			for (Module module : modules) {
				for (ServiceDefinition<?> service : module.getDefinitions()) {
					final Marker<?> marker = service.getMarker();
					if (!service.isOverride() && definitions.containsKey(marker)) {
						throw new ApplicationException(String.format("Service (%s) already declared", marker));
					}
					definitions.put(marker, service);
				}
			}
		}

		public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
			final Marker<T> resourceMarker = InternalUtils.generateMarker(resourceClass, annotations);
			if (!services.containsKey(resourceMarker)) {
				@SuppressWarnings("unchecked")
				final ServiceDefinition<T> service = (ServiceDefinition<T>) definitions.get(resourceMarker);
				//todo: throw error if null
				if (service == null) {
					throw new ApplicationException(String.format("Cannot find resource '%s'", resourceMarker));
				}
				final ServiceBuilderImpl<T> builder = new ServiceBuilderImpl<T>(this, service);
				for (Module module : modules) {
					builder.addContributors(module);
					builder.addInterceptors(module);
				}
				register(builder);
			}
			return get(resourceMarker);
		}

		public Set<Marker<?>> getMarkers() {
			return definitions.keySet();
		}

		public <T> Set<T> find(Marker<T> marker) {
			//noinspection unchecked
			return CollectionUtils.newSet(get(marker));
		}

		public <T> T get(final Marker<T> marker) {
			return marker.getServiceClass().cast(services.get(marker));
		}

		public <T> void register(final ServiceBuilder<T> builder) {
			final Marker<T> marker = builder.getMarker();
			if (marker.getScope() == null || marker.getScope().equals(Singleton.class)) {
				final CachedBuilder<T> cachedBuilder = new CachedBuilder<T>(builder);
				final T proxy = proxyFactory.createProxy(cachedBuilder);
				services.put(marker, proxy);
			}
		}
	}
}
