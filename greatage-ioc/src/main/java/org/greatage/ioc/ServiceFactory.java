/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.logging.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class represent implementation of service definition that instantiates service. It is based on building service
 * by invoking configured module method.
 *
 * @param <T> service type
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

	private final Logger logger;

	/**
	 * Creates new instance of service definition with defined module class and build method, service class. Build method
	 * must have return equal to service type and be annotated with {@link Build} annotation.
	 *
	 * @param logger		system logger
	 * @param factoryClass  module class
	 * @param factoryMethod build method
	 */
	ServiceFactory(final Logger logger, final Class<?> factoryClass, final Method factoryMethod) {
		this.logger = logger;
		this.factoryClass = factoryClass;
		this.factoryMethod = factoryMethod;

		//noinspection unchecked
		serviceClass = (Class<T>) factoryMethod.getReturnType();

		final Build build = factoryMethod.getAnnotation(Build.class);
		final String proposedId = InternalUtils.generateServiceId(build.value(), build.id());
		serviceId = proposedId != null ? proposedId : serviceClass.getName();
		scope = build.scope();
		override = build.override();
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
	 * {@inheritDoc} It builds service instance by invoking configured module method.
	 */
	public T build(final ServiceResources<T> resources) {
		logger.info("Building service (%s, %s) from module (%s, %s)", serviceId, serviceClass, factoryClass,
				factoryMethod);

		try {
			final Object moduleInstance =
					Modifier.isStatic(factoryMethod.getModifiers()) ? null : resources.getResource(factoryClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, factoryMethod);
			return serviceClass.cast(factoryMethod.invoke(moduleInstance, parameters));
		} catch (Exception e) {
			throw new ApplicationException(
					String.format("Can't create service of class '%s' with id '%s'", serviceClass, serviceId), e);
		}
	}
}
