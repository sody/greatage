/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents service resources proxy that is used during service decorate phase and adds service instance as
 * additional resource.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceDecorateResources<T> extends ServiceAdditionalResources<T> {
	private final T serviceInstance;

	/**
	 * Creates new instance of service resources proxy for decorate phase with defined service resource delegate and
	 * service instance.
	 *
	 * @param delegate		service resources delegate
	 * @param serviceInstance service instance
	 */
	ServiceDecorateResources(final ServiceResources<T> delegate, final T serviceInstance) {
		super(delegate);
		this.serviceInstance = serviceInstance;
	}

	/**
	 * Gets service instance.
	 *
	 * @return service instance
	 */
	public T getServiceInstance() {
		return serviceInstance;
	}

	/**
	 * {@inheritDoc} Gets service instance as additional resource.
	 *
	 * @return service instance or null
	 */
	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (resourceClass.isInstance(serviceInstance)) {
			return resourceClass.cast(serviceInstance);
		}
		return null;
	}
}
