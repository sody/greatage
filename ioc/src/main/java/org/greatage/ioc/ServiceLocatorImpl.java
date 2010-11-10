/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.scope.GlobalScope;
import org.greatage.ioc.scope.Scope;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorImpl implements ServiceLocator {
	private final Map<String, ServiceHolder<?>> servicesById = CollectionUtils.newMap();
	private final Logger logger;

	ServiceLocatorImpl(final List<Module> modules) {
		final Map<String, Service<?>> services = CollectionUtils.newMap();
		for (Module module : modules) {
			for (Service service : module.getServices()) {
				final String serviceId = service.getServiceId();
				if (services.containsKey(serviceId) && !service.isOverride()) {
					throw new IllegalStateException(String.format("Service with id '%s' already declared", serviceId));
				}
				services.put(serviceId, service);
			}
		}

		final Scope internalScope = new GlobalScope();

		final StringBuilder statisticsBuilder = new StringBuilder("Services:\n");
		for (Map.Entry<String, Service<?>> entry : services.entrySet()) {
			final String serviceId = entry.getKey();
			final Service<?> service = entry.getValue();
			final List<Configurator<?>> configurators = CollectionUtils.newList();
			final List<Decorator<?>> decorators = CollectionUtils.newList();
			final List<Interceptor<?>> interceptors = CollectionUtils.newList();
			for (Module module : modules) {
				configurators.addAll(module.getConfigurators(service));
				decorators.addAll(module.getDecorators(service));
				interceptors.addAll(module.getInterceptors(service));
			}
			final ServiceHolder<?> holder = createServiceHolder(service, configurators, decorators, interceptors, internalScope);
			servicesById.put(serviceId, holder);

			statisticsBuilder.append(String.format("\t%s[ %s ]( %s )\n", serviceId, service.getScope(), service.getServiceClass()));
		}

		final LoggerSource loggerSource = getService(LoggerSource.class);
		logger = loggerSource.getLogger(ServiceLocator.class);
		logger.info(statisticsBuilder.toString());
	}

	public <T> T getService(String id, Class<T> serviceClass) {
		if (servicesById.containsKey(id)) {
			final ServiceHolder holder = servicesById.get(id);
			final Object service = holder.getService();
			return serviceClass.cast(service);
		}
		throw new IllegalStateException(String.format("Can't find service with id %s", id));
	}

	public <T> T getService(Class<T> serviceClass) {
		final List<T> services = findServices(serviceClass);
		if (services.size() == 1) {
			return services.get(0);
		}
		throw new IllegalStateException(String.format("Can't find service of class %s", serviceClass));
	}

	public <T> List<T> findServices(Class<T> serviceClass) {
		final List<T> result = CollectionUtils.newList();
		for (ServiceHolder serviceHolder : servicesById.values()) {
			if (serviceClass.isAssignableFrom(serviceHolder.getServiceClass())) {
				final Object service = serviceHolder.getService();
				result.add(serviceClass.cast(service));
			}
		}
		return result;
	}

	public Logger getLogger() {
		return logger;
	}

	@SuppressWarnings({"unchecked"})
	private ServiceHolder createServiceHolder(final Service<?> service,
											  final List<Configurator<?>> configurators,
											  final List<Decorator<?>> decorators,
											  final List<Interceptor<?>> interceptors,
											  final Scope internalScope) {
		final ServiceResources resources = new ServiceInitialResources(this, service);
		final ObjectBuilder builder = new ServiceBuilder(service, resources, configurators, decorators);

		return new ServiceHolder(resources,
				service.isLazy() ? new LazyBuilder(resources, builder, interceptors) : builder,
				ScopeConstants.INTERNAL.equals(service.getScope()) ? internalScope : null);
	}
}
