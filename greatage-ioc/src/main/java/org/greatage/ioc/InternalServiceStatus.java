/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.proxy.ObjectBuilder;

import java.util.List;

/**
 * This class represents implementation of {@link ServiceStatus} that is used for internal services. Such services have
 * specific scope - something similar with global and can not be intercepted. They include {@link
 * org.greatage.ioc.proxy.ProxyFactory}, {@link org.greatage.ioc.logging.LoggerSource}, {@link
 * org.greatage.ioc.scope.ScopeManager}.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InternalServiceStatus<T> implements ServiceStatus<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private T serviceInstance;

	/**
	 * Creates new instance of internal service status with defined service locator, service definition, contribute
	 * definitions and decorate definitions.
	 *
	 * @param locator	  service locator
	 * @param service	  service definition
	 * @param contributors service contribute definitions
	 * @param decorators   service decorate definitions
	 */
	InternalServiceStatus(final ServiceLocator locator,
						  final Service<T> service,
						  final List<Contributor<T>> contributors,
						  final List<Decorator<T>> decorators) {
		this.resources = new ServiceInitialResources<T>(locator, service);
		this.builder = new ServiceBuilder<T>(resources, service, contributors, decorators);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceId() {
		return resources.getServiceId();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceScope() {
		return resources.getServiceScope();
	}

	/**
	 * Gets service instance and creates new if it is not built yet.
	 *
	 * @return service instance
	 */
	public T getService() {
		if (serviceInstance == null) {
			serviceInstance = builder.build();
		}
		return serviceInstance;
	}
}
