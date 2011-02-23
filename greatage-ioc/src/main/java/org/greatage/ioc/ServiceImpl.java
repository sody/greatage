/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.logging.Logger;

import java.lang.reflect.Constructor;

/**
 * This class represent default implementation service definition that automatically instantiates service. It is based
 * on building service by invoking service implementation constructor.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceImpl<T> implements Service<T> {
	private final Class<T> serviceClass;
	private final Class<? extends T> implementationClass;
	private final String serviceId;
	private final String scope;
	private final boolean override;

	private final Logger logger;

	/**
	 * Creates new instance of service definition with defined service identifier, service class, scope and override
	 * option.
	 *
	 * @param logger	   system logger
	 * @param serviceId	service identifier
	 * @param serviceClass service class
	 * @param scope		service scope
	 * @param override	 option that determines is service overrides its default definition
	 */
	ServiceImpl(final Logger logger,
				final String serviceId,
				final Class<T> serviceClass,
				final String scope,
				final boolean override) {
		this(logger, serviceId, serviceClass, serviceClass, scope, override);
	}

	/**
	 * Creates new instance of service definition with defined service identifier, service class, service implementation
	 * class, scope and override option.
	 *
	 * @param logger			  system logger
	 * @param serviceId		   service identifier
	 * @param serviceClass		service class
	 * @param implementationClass service implementation class
	 * @param scope			   service scope
	 * @param override			option that determines is service overrides its default definition
	 */
	ServiceImpl(final Logger logger,
				final String serviceId,
				final Class<T> serviceClass,
				final Class<? extends T> implementationClass,
				final String scope,
				final boolean override) {
		this.logger = logger;
		this.serviceClass = serviceClass;
		this.implementationClass = implementationClass;
		this.serviceId = serviceId;
		this.scope = scope;
		this.override = override;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getServiceClass() {
		return serviceClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOverride() {
		return override;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * {@inheritDoc} It automatically builds service instance by invoking service implementation constructor.
	 */
	public T build(final ServiceResources<T> resources) {
		logger.info("Building service (%s, %s) from (%s)", serviceId, serviceClass, implementationClass);

		try {
			final Constructor constructor = implementationClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(resources, constructor);
			return implementationClass.cast(constructor.newInstance(parameters));
		} catch (Exception e) {
			throw new ApplicationException(
					String.format("Can't create service of class '%s' with id '%s'", serviceClass, serviceId), e);
		}
	}
}
