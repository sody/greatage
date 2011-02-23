/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.logging.Logger;
import org.greatage.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class represents default implementation of service contribution definition that distributively configures
 * services. It is based on configuring service by invoking module method.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ContributorImpl<T> implements Contributor<T> {
	private final Class<?> moduleClass;
	private final Method configureMethod;

	private final Class<T> serviceClass;
	private final String serviceId;

	private final Logger logger;

	/**
	 * Creates new instance of service contribution definition with defined module class and method used for service
	 * configuration. Configuration method must have void return type and be annotated with {@link Contribute} annotation.
	 *
	 * @param logger		  system logger
	 * @param moduleClass	 module class
	 * @param configureMethod module method used for service configuration
	 * @throws ApplicationException if configure method doesn't correspond to requirements
	 */
	ContributorImpl(final Logger logger, final Class<?> moduleClass, final Method configureMethod) {
		this.logger = logger;
		this.moduleClass = moduleClass;
		this.configureMethod = configureMethod;

		if (!configureMethod.getReturnType().equals(Void.TYPE)) {
			throw new ApplicationException("Configuration method can not return anu value");
		}

		final Contribute contribute = configureMethod.getAnnotation(Contribute.class);
		serviceId = !StringUtils.isEmpty(contribute.serviceId()) ? contribute.serviceId() : null;
		//noinspection unchecked
		serviceClass = contribute.value();
	}

	/**
	 * {@inheritDoc} This contributor supports service if its service identifier or service class correspond to configured
	 * ones.
	 */
	public boolean supports(final Service service) {
		return serviceId != null ?
				service.getServiceId().equals(serviceId) :
				serviceClass.isAssignableFrom(service.getServiceClass());
	}

	/**
	 * {@inheritDoc} It configures service by invoking configured module method.
	 */
	public void contribute(final ServiceResources<T> resources) {
		logger.info("Configuring service (%s, %s) from module (%s, %s)", resources.getServiceId(),
				resources.getServiceClass(), moduleClass, configureMethod);

		try {
			final Object moduleInstance =
					Modifier.isStatic(configureMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, configureMethod);
			configureMethod.invoke(moduleInstance, parameters);
		} catch (Exception e) {
			throw new ApplicationException(
					String.format("Can't create service configuration with id '%s'", resources.getServiceId()), e);
		}
	}
}
