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

import org.greatage.ioc.annotations.Contribute;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class represents default implementation of service contribution definition that distributively configures services. It is
 * based on configuring service by invoking module method.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceContributorImpl<T> implements ServiceContributor<T> {
	private final Class<?> moduleClass;
	private final Method configureMethod;

	private final Marker<T> marker;

	/**
	 * Creates new instance of service contribution definition with defined module class and method used for service configuration.
	 * Configuration method must have void return type and be annotated with {@link Contribute} annotation.
	 *
	 * @param moduleClass	 module class
	 * @param configureMethod module method used for service configuration
	 * @throws ApplicationException if configure method doesn't correspond to requirements
	 */
	ServiceContributorImpl(final Class<?> moduleClass, final Method configureMethod) {
		this.moduleClass = moduleClass;
		this.configureMethod = configureMethod;

		if (!configureMethod.getReturnType().equals(void.class)) {
			throw new IllegalStateException("Configuration method can not return any value");
		}

		final Contribute contribute = configureMethod.getAnnotation(Contribute.class);
		//noinspection unchecked
		marker = InternalUtils.generateMarker(contribute.value(), configureMethod.getAnnotations());
	}

	public Marker<T> getMarker() {
		return marker;
	}

	/**
	 * {@inheritDoc} It configures service by invoking configured module method.
	 */
	public void contribute(final ServiceResources<T> resources) {
		try {
			final Object moduleInstance =
					Modifier.isStatic(configureMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, configureMethod);
			configureMethod.invoke(moduleInstance, parameters);
		} catch (Exception e) {
			throw new ApplicationException(String.format("Can't configure service (%s)", resources.getMarker()), e);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("module", moduleClass.getName());
		builder.append("method", configureMethod.getName());
		return builder.toString();
	}
}
