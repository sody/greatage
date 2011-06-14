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

import org.greatage.ioc.annotations.Intercept;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class represents default implementation of service interceptor definition that distributively configures service method
 * advices. It is based on intercepting service by invoking module method.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceInterceptorImpl<T> implements ServiceInterceptor<T> {
	private final Class<?> moduleClass;
	private final Method interceptMethod;

	private final Marker<T> marker;

	/**
	 * Creates new instance of service interceptor definition with defined module class and method used for service interception.
	 * Interception method must have {@link org.greatage.ioc.proxy.Interceptor} return type and be annotated with {@link
	 * org.greatage.ioc.annotations.Intercept} annotation.
	 *
	 * @param moduleClass	 module class
	 * @param interceptMethod module method used for service interception
	 */
	ServiceInterceptorImpl(final Class<?> moduleClass, final Method interceptMethod) {
		this.moduleClass = moduleClass;
		this.interceptMethod = interceptMethod;

		if (!interceptMethod.getReturnType().equals(void.class)) {
			throw new IllegalStateException("Interception method can not return any value");
		}

		final Intercept intercept = interceptMethod.getAnnotation(Intercept.class);
		//noinspection unchecked
		marker = InternalUtils.generateMarker(intercept.value(), interceptMethod.getAnnotations());
	}

	public Marker<T> getMarker() {
		return marker;
	}

	/**
	 * {@inheritDoc} It intercepts service by invoking configured module method.
	 */
	public void intercept(final ServiceResources<T> resources) {
		try {
			final Object moduleInstance =
					Modifier.isStatic(interceptMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, interceptMethod);
			interceptMethod.invoke(moduleInstance, parameters);
		} catch (Exception e) {
			throw new ApplicationException(String.format("Can't intercept service (%s)", resources.getMarker()), e);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("module", moduleClass.getName());
		builder.append("method", interceptMethod.getName());
		return builder.toString();
	}
}
