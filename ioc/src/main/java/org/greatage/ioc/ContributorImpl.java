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
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ContributorImpl<T> implements Contributor<T> {
	private final Class<?> moduleClass;
	private final Method configureMethod;

	private final Class<T> serviceClass;
	private final String serviceId;

	ContributorImpl(final Class<?> moduleClass, final Method configureMethod) {
		this.moduleClass = moduleClass;
		this.configureMethod = configureMethod;

		if (!configureMethod.getReturnType().equals(Void.TYPE)) {
			throw new IllegalArgumentException("Configuration method can not return anu value");
		}

		final Contribute contribute = configureMethod.getAnnotation(Contribute.class);
		serviceId = !StringUtils.isEmpty(contribute.serviceId()) ? contribute.serviceId() : null;
		//noinspection unchecked
		serviceClass = contribute.value();
	}

	public boolean supports(final Service service) {
		return serviceId != null ?
				service.getServiceId().equals(serviceId) :
				serviceClass.isAssignableFrom(service.getServiceClass());
	}

	public void contribute(final ServiceResources<T> resources) {
		final Logger logger = resources.getResource(Logger.class);
		logger.info("Configuring service (%s, %s) from module (%s, %s)", resources.getServiceId(), resources.getServiceClass(), moduleClass, configureMethod);

		try {
			final Object moduleInstance = Modifier.isStatic(configureMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, configureMethod);
			configureMethod.invoke(moduleInstance, parameters);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Can't create service configuration with id '%s'", resources.getServiceId()), e);
		}
	}
}