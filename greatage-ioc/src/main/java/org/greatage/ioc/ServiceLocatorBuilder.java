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

import org.greatage.ioc.inject.DefaultInjector;
import org.greatage.ioc.inject.InjectionProvider;
import org.greatage.ioc.inject.Injector;
import org.greatage.ioc.inject.LoggerInjectionProvider;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.logging.Slf4jLoggerSource;
import org.greatage.ioc.proxy.JdkProxyFactory;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.scope.GlobalScope;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeManager;
import org.greatage.ioc.scope.ScopeManagerImpl;
import org.greatage.util.CollectionUtils;
import org.greatage.util.Locker;

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
	private final ServiceLocatorModule rootModule = new ServiceLocatorModule();
	private final Locker locker = new Locker();

	private Injector injector;
	private final Collection<Module> modules = CollectionUtils.newList();
	private final Map<Marker<?>, ServiceDefinition<?>> services = CollectionUtils.newMap();
	private final Map<Class<?>, Object> cache = CollectionUtils.newMap();

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

		final LoggerSource fakeLoggerSource = new Slf4jLoggerSource();
		final ProxyFactory fakeProxyFactory = new JdkProxyFactory();

		final Scope scope = new GlobalScope();
		final ScopeManager fakeScopeManager = new ScopeManagerImpl(CollectionUtils.<Scope, Scope>newList(scope));
		cache.put(Scope.class, scope);

		injector = createInjector(fakeLoggerSource, fakeProxyFactory, fakeScopeManager);

		final ProxyFactory proxyFactory = getService(ProxyFactory.class);
		final LoggerSource loggerSource = getService(LoggerSource.class);
		final ScopeManager scopeManager = getService(ScopeManager.class);

		injector = createInjector(loggerSource, proxyFactory, scopeManager);

		return getService(ServiceLocator.class);
	}

	private DefaultInjector createInjector(final LoggerSource loggerSource,
										   final ProxyFactory proxyFactory,
										   final ScopeManager scopeManager) {
		final Logger logger = loggerSource.getLogger(Injector.class);
		final LoggerInjectionProvider loggerProvider = new LoggerInjectionProvider(loggerSource);
		final InternalInjectionProvider internalProvider = new InternalInjectionProvider();
		final List<InjectionProvider> providers = CollectionUtils.newList(loggerProvider, internalProvider);
		return new DefaultInjector(providers, logger, proxyFactory, scopeManager);
	}

	private <T> T getService(final Class<T> serviceClass) {
		if (!cache.containsKey(serviceClass)) {
			final Marker<T> marker = Marker.get(serviceClass);
			@SuppressWarnings("unchecked")
			final ServiceDefinition<T> service = (ServiceDefinition<T>) services.get(marker);
			final List<ServiceContributor<T>> contributors = CollectionUtils.newList();
			final List<ServiceDecorator<T>> decorators = CollectionUtils.newList();
			for (Module module : modules) {
				contributors.addAll(module.getContributors(marker));
				decorators.addAll(module.getDecorators(marker));
			}

			final T serviceInstance = injector.createService(service, contributors, decorators);
			cache.put(serviceClass, serviceInstance);
		}
		return serviceClass.cast(cache.get(serviceClass));
	}

	class InternalInjectionProvider implements InjectionProvider {
		public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
			return getService(resourceClass);
		}
	}
}
