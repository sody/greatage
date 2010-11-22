/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.scope.ScopeManager;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorImpl implements ServiceLocator {
	private final Map<String, ServiceStatus<?>> servicesById = CollectionUtils.newConcurrentMap();
	private final Set<Class<?>> internalServices = CollectionUtils.newSet();

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

		internalServices.add(LoggerSource.class);
		internalServices.add(ProxyFactory.class);
		internalServices.add(ScopeManager.class);


		final ServiceStatus<ServiceLocator> serviceLocatorStatus = new SimpleHolder<ServiceLocator>(ServiceLocator.class.getSimpleName(), ServiceLocator.class, this);
		servicesById.put(serviceLocatorStatus.getServiceId(), serviceLocatorStatus);
		int maxLength = serviceLocatorStatus.getServiceId().length();
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
			final ServiceStatus<?> status = createServiceHolder(service, configurators, decorators, interceptors);
			servicesById.put(serviceId, status);
			if (serviceId.length() > maxLength) {
				maxLength = serviceId.length();
			}
		}

		final StringBuilder statisticsBuilder = new StringBuilder("Services:\n");
		final String format = "%" + maxLength + "s[%s] : %s\n";
		for (ServiceStatus<?> status : servicesById.values()) {
			statisticsBuilder.append(String.format(format, status.getServiceId(), status.getServiceScope(), status.getServiceClass()));
		}

		final LoggerSource loggerSource = getService(LoggerSource.class);
		logger = loggerSource.getLogger(ServiceLocator.class);
		logger.info(statisticsBuilder.toString());
	}

	public Set<String> getServiceIds() {
		return CollectionUtils.newSet(servicesById.keySet());
	}

	public ServiceStatus<?> getServiceStatus(final String id) {
		return servicesById.get(id);
	}

	public <T> T getService(final String id, final Class<T> serviceClass) {
		if (servicesById.containsKey(id)) {
			final ServiceStatus<?> status = servicesById.get(id);
			final Object service = status.getService();
			return serviceClass.cast(service);
		}
		throw new IllegalStateException(String.format("Can't find service with id %s", id));
	}

	public <T> T getService(final Class<T> serviceClass) {
		final Set<T> services = findServices(serviceClass);
		if (services.size() == 1) {
			return services.iterator().next();
		}
		throw new IllegalStateException(String.format("Can't find service of class %s", serviceClass));
	}

	public <T> Set<T> findServices(final Class<T> serviceClass) {
		final Set<T> result = CollectionUtils.newSet();
		for (ServiceStatus<?> serviceStatus : servicesById.values()) {
			if (serviceClass.isAssignableFrom(serviceStatus.getServiceClass())) {
				final Object service = serviceStatus.getService();
				result.add(serviceClass.cast(service));
			}
		}
		return result;
	}

	@SuppressWarnings({"unchecked"})
	private ServiceStatus<?> createServiceHolder(final Service<?> service,
												 final List<Configurator<?>> configurators,
												 final List<Decorator<?>> decorators,
												 final List<Interceptor<?>> interceptors) {
		return isInternal(service) ?
				new InternalService(this, service, configurators, decorators) :
				new ScopedService(this, service, configurators, decorators, interceptors);
	}

	private boolean isInternal(final Service<?> service) {
		return internalServices.contains(service.getServiceClass());
	}
}
