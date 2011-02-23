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
 * This class represents default {@link ServiceLocator} implementation that is used as main entry point of Great Age IoC
 * container. TODO: make constructor that defines internal logger
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorImpl implements ServiceLocator {
	private final Map<String, ServiceStatus<?>> servicesById = CollectionUtils.newConcurrentMap();
	private final Set<Class<?>> internalServices = CollectionUtils.newSet();

	private final Logger logger;

	/**
	 * Creates new instance of service locator with defined modules.
	 *
	 * @param logger  system logger
	 * @param modules modules
	 */
	ServiceLocatorImpl(final Logger logger, final List<Module> modules) {
		this.logger = logger;
		final Map<String, Service<?>> services = CollectionUtils.newMap();
		for (Module module : modules) {
			for (Service service : module.getServices()) {
				final String serviceId = service.getServiceId();
				if (services.containsKey(serviceId) && !service.isOverride()) {
					throw new ApplicationException(String.format("Service with id '%s' already declared", serviceId));
				}
				services.put(serviceId, service);
			}
		}

		// initializing internal services collection
		internalServices.add(LoggerSource.class);
		internalServices.add(ProxyFactory.class);
		internalServices.add(ScopeManager.class);

		final ServiceStatus<ServiceLocator> serviceLocatorStatus = new ServiceLocatorStatus(this);
		servicesById.put(serviceLocatorStatus.getServiceId(), serviceLocatorStatus);
		int maxLength = serviceLocatorStatus.getServiceId().length();
		for (Map.Entry<String, Service<?>> entry : services.entrySet()) {
			final String serviceId = entry.getKey();
			final Service<?> service = entry.getValue();
			final List<Contributor<?>> contributors = CollectionUtils.newList();
			final List<Decorator<?>> decorators = CollectionUtils.newList();
			final List<Interceptor<?>> interceptors = CollectionUtils.newList();
			for (Module module : modules) {
				contributors.addAll(module.getContributors(service));
				decorators.addAll(module.getDecorators(service));
				interceptors.addAll(module.getInterceptors(service));
			}
			final ServiceStatus<?> status = createServiceStatus(service, contributors, decorators, interceptors);
			servicesById.put(serviceId, status);
			if (serviceId.length() > maxLength) {
				maxLength = serviceId.length();
			}
		}

		// building statistics for log
		final StringBuilder statisticsBuilder = new StringBuilder("Services:\n");
		final String format = "%" + maxLength + "s[%s] : %s\n";
		for (ServiceStatus<?> status : servicesById.values()) {
			statisticsBuilder.append(String.format(format,
					status.getServiceId(), status.getServiceScope(), status.getServiceClass()));
		}
		logger.info(statisticsBuilder.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<String> getServiceIds() {
		return servicesById.keySet();
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceStatus<?> getServiceStatus(final String id) {
		return servicesById.get(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final String id, final Class<T> serviceClass) {
		if (servicesById.containsKey(id)) {
			final ServiceStatus<?> status = servicesById.get(id);
			final Object service = status.getService();
			return serviceClass.cast(service);
		}
		throw new ApplicationException(String.format("Can't find service with id %s", id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final Class<T> serviceClass) {
		final Set<T> services = findServices(serviceClass);
		if (services.size() == 1) {
			return services.iterator().next();
		}
		throw new ApplicationException(String.format("Can't find service of class %s", serviceClass));
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * Creates {@link ServiceStatus} instance for specified service with defined service contributors, decorators and
	 * interceptors.
	 *
	 * @param service	  service definition
	 * @param contributors service contributors
	 * @param decorators   service decorators
	 * @param interceptors service interceptors
	 * @return service status instance, not null
	 */
	@SuppressWarnings("unchecked")
	private ServiceStatus<?> createServiceStatus(final Service<?> service,
												 final List<Contributor<?>> contributors,
												 final List<Decorator<?>> decorators,
												 final List<Interceptor<?>> interceptors) {
		return isInternal(service) ?
				new InternalServiceStatus(this, service, contributors, decorators) :
				new ServiceStatusImpl(this, service, contributors, decorators, interceptors);
	}

	/**
	 * Checks if service definition is internal. Internal services are {@link ProxyFactory}, {@link LoggerSource} and
	 * {@link ScopeManager}.
	 *
	 * @param service service definition
	 * @return true if service is internal, false otherwise
	 */
	private boolean isInternal(final Service<?> service) {
		return internalServices.contains(service.getServiceClass());
	}
}
