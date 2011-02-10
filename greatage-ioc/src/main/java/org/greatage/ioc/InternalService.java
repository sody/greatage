/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.proxy.ObjectBuilder;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InternalService<T> implements ServiceStatus<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private T serviceInstance;

	InternalService(final ServiceLocator locator,
					final Service<T> service,
					final List<Contributor<T>> contributors,
					final List<Decorator<T>> decorators) {
		this.resources = new ServiceInitialResources<T>(locator, service);
		this.builder = new ServiceBuilder<T>(resources, service, contributors, decorators);
	}

	public String getServiceId() {
		return resources.getServiceId();
	}

	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}

	public String getServiceScope() {
		return resources.getServiceScope();
	}

	public T getService() {
		if (serviceInstance == null) {
			serviceInstance = builder.build();
		}
		return serviceInstance;
	}
}
