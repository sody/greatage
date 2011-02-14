/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents default {@link ServiceBindingOptions} implementation that is used to define service unique id,
 * service scope and is it overrides the existing service.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBindingOptionsImpl<T> implements ServiceBindingOptions {
	private final Class<T> serviceClass;
	private final Class<? extends T> implementationClass;

	private String serviceId;
	private String serviceScope;
	private boolean override;

	/**
	 * Creates new instance of service binding options with defined service class and service implementation class.
	 *
	 * @param serviceClass		service class
	 * @param implementationClass service implementation class
	 */
	ServiceBindingOptionsImpl(final Class<T> serviceClass, final Class<? extends T> implementationClass) {
		this.serviceClass = serviceClass;
		this.implementationClass = implementationClass;
		this.serviceId = serviceClass.getSimpleName();
		this.serviceScope = ScopeConstants.GLOBAL;
		this.override = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceBindingOptions withId(final String id) {
		serviceId = id;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceBindingOptions withScope(final String scope) {
		serviceScope = scope;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceBindingOptions override() {
		override = true;
		return this;
	}

	/**
	 * Creates new instance of configured service definition.
	 *
	 * @return new instance of configured service definition, not null
	 */
	public Service<T> createService() {
		return new ServiceImpl<T>(serviceId, serviceClass, implementationClass, serviceScope, override);
	}
}
