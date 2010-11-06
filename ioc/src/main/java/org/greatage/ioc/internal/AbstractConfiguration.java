/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal;

import org.greatage.ioc.ServiceResources;

import java.lang.reflect.Constructor;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractConfiguration<T, V, C> {
	private final ServiceResources<T> resources;

	protected AbstractConfiguration(final ServiceResources<T> resources) {
		this.resources = resources;
	}

	protected V newInstance(final Class<? extends V> valueClass) {
		try {
			final Constructor constructor = valueClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(resources, constructor);
			return valueClass.cast(constructor.newInstance(parameters));
		} catch (Exception e) {
			throw new RuntimeException(String.format("Can't create object of class '%s'", valueClass), e);
		}
	}

	protected abstract C build();

}
