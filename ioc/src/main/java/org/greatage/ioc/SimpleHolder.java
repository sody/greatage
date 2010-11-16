/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SimpleHolder<T> implements ServiceStatus<T> {
	private final String serviceId;
	private final Class<T> serviceClass;
	private final T serviceInstance;

	public SimpleHolder(final String serviceId, final Class<T> serviceClass, final T serviceInstance) {
		this.serviceId = serviceId;
		this.serviceClass = serviceClass;
		this.serviceInstance = serviceInstance;
	}

	public String getServiceId() {
		return serviceId;
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public String getServiceScope() {
		return ScopeConstants.GLOBAL;
	}

	public T getService() {
		return serviceInstance;
	}
}
