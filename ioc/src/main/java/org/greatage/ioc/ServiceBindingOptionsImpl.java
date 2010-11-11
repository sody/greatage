/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBindingOptionsImpl<T> implements ServiceBindingOptions {
	private final Class<T> serviceClass;
	private final Class<? extends T> implementationClass;

	private String serviceId;
	private String serviceScope;
	private boolean override;

	ServiceBindingOptionsImpl(final Class<T> serviceClass, final Class<? extends T> implementationClass) {
		this.serviceClass = serviceClass;
		this.implementationClass = implementationClass;
		this.serviceId = serviceClass.getSimpleName();
		this.serviceScope = ScopeConstants.GLOBAL;
		this.override = false;
	}

	public ServiceBindingOptions withId(final String id) {
		serviceId = id;
		return this;
	}

	public ServiceBindingOptions withScope(final String scope) {
		serviceScope = scope;
		return this;
	}

	public ServiceBindingOptions override() {
		override = true;
		return this;
	}

	public Service<T> createService() {
		return new ServiceImpl<T>(serviceId, serviceClass, implementationClass, serviceScope, override);
	}
}
