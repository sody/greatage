/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * This class represents default {@link ServiceBinder} implementation that distributively binds service interfaces to
 * their automatically built instances. It is based on binding services by invoking module static method.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBinderImpl implements ServiceBinder {
	private final List<ServiceBindingOptionsImpl> services = CollectionUtils.newList();

	/**
	 * Creates new instance of service binder that distributively binds service interfaces to their automatically built
	 * instances.
	 */
	ServiceBinderImpl() {
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> ServiceBindingOptions bind(final Class<T> implementationClass) {
		return bind(implementationClass, implementationClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> ServiceBindingOptions bind(final Class<T> serviceClass, final Class<? extends T> implementationClass) {
		final ServiceBindingOptionsImpl<T> options =
				new ServiceBindingOptionsImpl<T>(serviceClass, implementationClass);
		services.add(options);
		return options;
	}

	/**
	 * Creates list of configured automatically built service definitions.
	 *
	 * @return list of configured automatically built service definitions or empty list
	 */
	public List<Service> getServices() {
		final List<Service> result = CollectionUtils.newList();
		for (ServiceBindingOptionsImpl options : services) {
			result.add(options.createService());
		}
		return result;
	}
}
