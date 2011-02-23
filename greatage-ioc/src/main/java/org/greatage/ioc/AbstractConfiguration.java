/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import java.lang.reflect.Constructor;

/**
 * This class represents abstract implementation of all types of service configuration. Adds auto build functionality
 * for configuration items instantiation.
 *
 * @param <T> service type
 * @param <V> type of configuration items
 * @param <C> configuration resulting type
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractConfiguration<T, V, C> {
	private final ServiceResources<T> resources;

	/**
	 * Creates new instance of service configuration with defined service resources.
	 *
	 * @param resources service resources
	 */
	protected AbstractConfiguration(final ServiceResources<T> resources) {
		this.resources = resources;
	}

	/**
	 * Automatically builds new instance of service configuration item by specified item class.
	 *
	 * @param valueClass service configuration item class
	 * @return automatically built new instance of service configuration item
	 */
	protected V newInstance(final Class<? extends V> valueClass) {
		try {
			final Constructor constructor = valueClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(resources, constructor);
			return valueClass.cast(constructor.newInstance(parameters));
		} catch (Exception e) {
			throw new ApplicationException(String.format("Can't create object of class '%s'", valueClass), e);
		}
	}

	/**
	 * Builds resulting service configuration. Used for calculating configuration instance for service construction
	 * method.
	 *
	 * @return resulting service configuration
	 */
	protected abstract C build();
}
