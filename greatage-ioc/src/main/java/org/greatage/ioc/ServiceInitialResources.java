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

import org.greatage.ioc.annotations.Inject;
import org.greatage.ioc.annotations.Symbol;
import org.greatage.ioc.coerce.TypeCoercer;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.symbol.SymbolSource;

import java.lang.annotation.Annotation;

/**
 * This class represents default {@link ServiceResources} implementation that is used for retrieving default service
 * resources such as other services, symbols or specific service logger instance.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceInitialResources<T> implements ServiceResources<T> {
	private final ServiceLocator locator;
	private final String serviceId;
	private final Class<T> serviceClass;
	private final String serviceScope;

	/**
	 * Creates new instance of service initial resources with defined service locator and service definition.
	 *
	 * @param locator service locator
	 * @param service service definition
	 */
	ServiceInitialResources(final ServiceLocator locator, final ServiceDefinition<T> service) {
		this(locator, service.getServiceId(), service.getServiceClass(), service.getScope());
	}

	/**
	 * Creates new instance of service initial resources with defined service locator, service identifier, service class
	 * and scope.
	 *
	 * @param locator	  service locator
	 * @param serviceId	service identifier
	 * @param serviceClass service class
	 * @param serviceScope service scope
	 */
	ServiceInitialResources(final ServiceLocator locator, final String serviceId, final Class<T> serviceClass,
							final String serviceScope) {
		this.locator = locator;
		this.serviceId = serviceId;
		this.serviceClass = serviceClass;
		this.serviceScope = serviceScope;
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
	public String getServiceScope() {
		return serviceScope;
	}

	/**
	 * {@inheritDoc}
	 */
	public <E> E getResource(final Class<E> resourceClass, final Annotation... annotations) {
		// if resource type is Logger trying to retrieve service specific logger using LoggerSource service
		if (Logger.class.equals(resourceClass)) {
			return resourceClass.cast(getLogger());
		}

		// if annotated with Symbol annotation trying to find symbol using SymbolSource service
		final Symbol symbol = findAnnotation(annotations, Symbol.class);
		if (symbol != null) {
			return getSymbol(resourceClass, symbol.value());
		}

		// if annotated with Inject annotation trying to find service with specified service identifier
		final Inject inject = findAnnotation(annotations, Inject.class);
		if (inject != null) {
			final String serviceId = InternalUtils.generateServiceId(inject.value(), inject.id());
			if (serviceId != null) {
				return locator.getService(serviceId, resourceClass);
			}
		}

		// trying to find service by resource type by default
		return locator.getService(resourceClass);
	}

	/**
	 * Finds annotation with specified class in annotation array.
	 *
	 * @param annotations	 annotation array
	 * @param annotationClass annotation class to find
	 * @param <A>             annotation type
	 * @return annotation instance or null if not found
	 */
	private static <A extends Annotation> A findAnnotation(final Annotation[] annotations,
														   final Class<A> annotationClass) {
		for (Annotation annotation : annotations) {
			if (annotationClass.isInstance(annotation)) {
				return annotationClass.cast(annotation);
			}
		}
		return null;
	}

	/**
	 * Gets coerced value for specified symbol expression as service resource.
	 *
	 * @param resourceClass class to coerce to
	 * @param symbol		symbol expression
	 * @param <E>           resource type
	 * @return coerced value for specified symbol expression
	 */
	private <E> E getSymbol(final Class<E> resourceClass, final String symbol) {
		final SymbolSource symbolSource = locator.getService(SymbolSource.class);
		final String value = symbolSource.getValue(symbol);

		final TypeCoercer typeCoercer = locator.getService(TypeCoercer.class);
		return typeCoercer.coerce(value, resourceClass);
	}

	/**
	 * Gets logger as injected resource for service represented by this service resources.
	 *
	 * @return service logger
	 */
	private Logger getLogger() {
		final LoggerSource loggerSource = locator.getService(LoggerSource.class);
		return loggerSource.getLogger(serviceClass);
	}
}
