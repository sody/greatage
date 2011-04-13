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

import java.lang.annotation.Annotation;
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
	private final Class<?> moduleClass;
	private final Method buildMethod;

	private final Marker<T> marker;
	private final Class<? extends Annotation> scope;
	private final boolean override;
	private final boolean eager;

	/**
	 * Creates new instance of service definition with defined module class and build method, service class. Build method must have
	 * return equal to service type and be annotated with {@link Build} annotation.
	 *
	 * @param moduleClass module class
	 * @param buildMethod build method
	 */
	ServiceDefinitionFactory(final Class<?> moduleClass, final Method buildMethod) {
		this.moduleClass = moduleClass;
		this.buildMethod = buildMethod;

		final Build build = buildMethod.getAnnotation(Build.class);
		override = build.override();
		eager = build.eager();

		scope = InternalUtils.findScope(buildMethod.getAnnotations());

		//noinspection unchecked
		marker = (Marker<T>) InternalUtils.generateMarker(buildMethod.getReturnType(), buildMethod.getAnnotations());
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
	public Class<? extends Annotation> getScope() {
		return scope;
	}

	/**
	 * {@inheritDoc} It builds service instance by invoking configured module method.
	 */
	public T build(final ServiceResources<T> resources) {
		try {
			final Object moduleInstance = Modifier.isStatic(buildMethod.getModifiers()) ? null :
					resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, buildMethod);
			return marker.getServiceClass().cast(buildMethod.invoke(moduleInstance, parameters));
		} catch (Exception e) {
			throw new ApplicationException(String.format("Can't create service (%s)", marker), e);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("module", moduleClass.getName());
		builder.append("method", buildMethod.getName());
		return builder.toString();
	}
}
