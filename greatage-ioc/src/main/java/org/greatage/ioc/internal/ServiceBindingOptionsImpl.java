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

package org.greatage.ioc.internal;

import org.greatage.ioc.Key;
import org.greatage.ioc.ServiceBindingOptions;
import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.ioc.services.ServiceDefinition;
import org.greatage.util.AnnotationFactory;

import java.lang.annotation.Annotation;

/**
 * This class represents default {@link org.greatage.ioc.ServiceBindingOptions} implementation that is used to define
 * service unique id, service scope and is it overrides the existing service.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBindingOptionsImpl<T> implements ServiceBindingOptions {
	private final Class<T> serviceClass;
	private final Class<? extends T> implementationClass;

	private Class<? extends Annotation> serviceScope;
	private Annotation annotation;
	private boolean override;
	private boolean eager;

	/**
	 * Creates new instance of service binding options with defined service class and service implementation class.
	 *
	 * @param serviceClass		service class
	 * @param implementationClass service implementation class
	 */
	ServiceBindingOptionsImpl(final Class<T> serviceClass, final Class<? extends T> implementationClass) {
		this.serviceClass = serviceClass;
		this.implementationClass = implementationClass;
		this.override = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceBindingOptions withScope(final Class<? extends Annotation> scope) {
		serviceScope = scope;
		return this;
	}

	public ServiceBindingOptions annotatedWith(final Annotation annotation) {
		this.annotation = annotation;
		return this;
	}

	public ServiceBindingOptions annotatedWith(final Class<? extends Annotation> annotationClass) {
		this.annotation = AnnotationFactory.create(annotationClass);
		return this;
	}

	public ServiceBindingOptions named(final String name) {
		this.annotation = new NamedImpl(name);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceBindingOptions override() {
		override = true;
		return this;
	}

	public ServiceBindingOptions eager() {
		eager = true;
		return this;
	}

	/**
	 * Creates new instance of configured service definition.
	 *
	 * @return new instance of configured service definition, not null
	 */
	public ServiceDefinition<T> createService() {
		final Key<T> key = Key.get(InternalUtils.generateMarker(serviceClass, implementationClass.getAnnotations()));
		if (annotation != null) {
			key.qualified(annotation);
		}
		if (serviceScope != null) {
			key.scoped(serviceScope);
		}
		return new ServiceDefinitionImpl<T>(key, implementationClass, override, eager);
	}
}
