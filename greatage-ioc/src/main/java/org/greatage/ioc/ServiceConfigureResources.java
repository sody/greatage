/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents service resources proxy that is used during service configure phase and adds service
 * configuration as additional resource.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceConfigureResources<T> extends ServiceAdditionalResources<T> {
	private final Object configuration;

	/**
	 * Creates new instance of service resources proxy for configure phase with defined service resources delegate and
	 * service configuration.
	 *
	 * @param delegate	  service resources delegate
	 * @param configuration service configuration
	 */
	ServiceConfigureResources(final ServiceResources<T> delegate, final Object configuration) {
		super(delegate);
		this.configuration = configuration;
	}

	/**
	 * {@inheritDoc} Gets service configuration instance as additional resource.
	 *
	 * @return service configuration or null
	 */
	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (resourceClass.isInstance(configuration)) {
			return resourceClass.cast(configuration);
		}
		return null;
	}
}
