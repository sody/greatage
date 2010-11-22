/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import java.lang.annotation.Annotation;

/**
 * This class represents service resources that may be provided to a service when it initializes, are configurated or
 * are decorated. It also provides access to other services.
 *
 * @author Ivan Khalopik
 * @param <T>        service type
 * @since 1.0
 */
public interface ServiceResources<T> {

	/**
	 * Gets unique service id used to locate the service object.
	 *
	 * @return unique service id, not null
	 */
	String getServiceId();

	/**
	 * Gets service interface that both service implementation and service proxy will implement.
	 *
	 * @return service interface, not null
	 */
	Class<T> getServiceClass();

	/**
	 * Gets service scope. {@link org.greatage.ioc.scope.ScopeManager} service must be configured to understand this value
	 * of scope.
	 *
	 * @return service scope, not null
	 */
	String getServiceScope();

	/**
	 * Gets resource to service by its type and annotations used.
	 *
	 * @param resourceClass resource class
	 * @param annotations   resource annotation
	 * @param <E>           resource type
	 * @return requested resource that implements specified class
	 * @throws RuntimeException if an error occurs instantiating resource
	 */
	<E> E getResource(Class<E> resourceClass, Annotation... annotations);

}
