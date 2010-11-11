/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.proxy.ObjectBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InternalHolder<T> implements ServiceStatus<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private T serviceInstance;

	InternalHolder(final ServiceResources<T> resources,
				   final ServiceBuilder<T> builder) {
		this.resources = resources;
		this.builder = builder;
	}

	public T getService() {
		if (serviceInstance == null) {
			serviceInstance = builder.build();
		}
		return serviceInstance;
	}

	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}
}
