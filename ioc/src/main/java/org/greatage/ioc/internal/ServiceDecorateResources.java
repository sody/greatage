/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal;

import org.greatage.ioc.ServiceResources;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceDecorateResources<T> extends ServiceAdditionalResources<T> {
	private final T serviceInstance;

	ServiceDecorateResources(final ServiceResources<T> delegate, final T serviceInstance) {
		super(delegate);
		this.serviceInstance = serviceInstance;
	}

	public T getServiceInstance() {
		return serviceInstance;
	}

	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (resourceClass.isInstance(serviceInstance)) {
			return resourceClass.cast(serviceInstance);
		}
		return null;
	}
}
