/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal;

import org.greatage.ioc.Service;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.services.Logger;
import org.greatage.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceFactory<T> implements Service<T> {
	private final Class<?> factoryClass;
	private final Method factoryMethod;

	private final Class<T> serviceClass;
	private final String serviceId;
	private final String scope;
	private final boolean override;
	private final boolean proxy;

	ServiceFactory(final Class<?> factoryClass, final Method factoryMethod) {
		this.factoryClass = factoryClass;
		this.factoryMethod = factoryMethod;

		//noinspection unchecked
		serviceClass = (Class<T>) factoryMethod.getReturnType();

		final Build build = factoryMethod.getAnnotation(Build.class);
		serviceId = !StringUtils.isEmpty(build.value()) ? build.value() : serviceClass.getSimpleName();
		scope = build.scope();
		override = build.override();
		proxy = build.lazy();
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

	public boolean isLazy() {
		return proxy;
	}

	public String getScope() {
		return scope;
	}

	public T build(final ServiceResources<T> resources) {
		final Logger logger = resources.getLogger();
		if (logger != null) {
			logger.info("Building service (%s, %s) from module (%s, %s)", serviceId, serviceClass, factoryClass, factoryMethod);
		}

		try {
			final Object moduleInstance = Modifier.isStatic(factoryMethod.getModifiers()) ? null : resources.getResource(factoryClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, factoryMethod);
			return serviceClass.cast(factoryMethod.invoke(moduleInstance, parameters));
		} catch (Exception e) {
			throw new RuntimeException(String.format("Can't create service of class '%s' with id '%s'", serviceClass, serviceId), e);
		}
	}
}
