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
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class represent implementation of service definition that instantiates service. It is based on building service by
 * invoking configured module method.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceDefinitionFactory<T> implements ServiceDefinition<T> {
	private final Class<?> factoryClass;
	private final Method factoryMethod;

	private final Marker<T> marker;
	private final String scope;
	private final boolean override;
	private final boolean eager;

	/**
	 * Creates new instance of service definition with defined module class and build method, service class. Build method must have
	 * return equal to service type and be annotated with {@link Build} annotation.
	 *
	 * @param factoryClass  module class
	 * @param factoryMethod build method
	 */
	ServiceDefinitionFactory(final Class<?> factoryClass, final Method factoryMethod) {
		this.factoryClass = factoryClass;
		this.factoryMethod = factoryMethod;

		final Build build = factoryMethod.getAnnotation(Build.class);
		scope = build.scope();
		override = build.override();
		eager = build.eager();

		//noinspection unchecked
		marker = (Marker<T>) InternalUtils.generateMarker(factoryMethod.getReturnType(), factoryMethod.getAnnotations());
	}

	/**
	 * {@inheritDoc}
	 */
	public Marker<T> getMarker() {
		return marker;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOverride() {
		return override;
	}

	public boolean isEager() {
		return eager;
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
		try {
			final Object moduleInstance =
					Modifier.isStatic(factoryMethod.getModifiers()) ? null : resources.getResource(factoryClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, factoryMethod);
			return marker.getServiceClass().cast(factoryMethod.invoke(moduleInstance, parameters));
		} catch (Exception e) {
			throw new ApplicationException(String.format("Can't create service (%s)", marker), e);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("module", factoryClass);
		builder.append("method", factoryMethod);
		return builder.toString();
	}
}
