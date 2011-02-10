/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents specific information about service inside the IoC container.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceStatus<T> {

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
	 * Retrieves service instance. It returns the service's proxy that implements the same interface as the actual service
	 * and instantiates the actual service only as needed with all configured method advices.
	 *
	 * @return service instance
	 */
	T getService();

}
