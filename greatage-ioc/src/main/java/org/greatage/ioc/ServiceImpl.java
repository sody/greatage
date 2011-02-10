/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.logging.Logger;

import java.lang.reflect.Constructor;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceImpl<T> implements Service<T> {
	private final Class<T> serviceClass;
	private final Class<? extends T> implementationClass;
	private final String serviceId;
	private final String scope;
	private final boolean override;

	ServiceImpl(final String serviceId, final Class<T> serviceClass, final String scope, final boolean override) {
		this(serviceId, serviceClass, serviceClass, scope, override);
	}

	ServiceImpl(final String serviceId,
				final Class<T> serviceClass,
				final Class<? extends T> implementationClass,
				final String scope,
				final boolean override) {
		this.serviceClass = serviceClass;
		this.implementationClass = implementationClass;
		this.serviceId = serviceId;
		this.scope = scope;
		this.override = override;
	}

	public String getServiceId() {
		return serviceId;
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public boolean isOverride() {
		return override;
	}

	public String getScope() {
		return scope;
	}

	public T build(final ServiceResources<T> resources) {
		final Logger logger = resources.getResource(Logger.class);
		logger.info("Building service (%s, %s) from (%s)", serviceId, serviceClass, implementationClass);

		try {
			final Constructor constructor = implementationClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(resources, constructor);
			return implementationClass.cast(constructor.newInstance(parameters));
		} catch (Exception e) {
			throw new RuntimeException(String.format("Can't create service of class '%s' with id '%s'", serviceClass, serviceId), e);
		}
	}
}
