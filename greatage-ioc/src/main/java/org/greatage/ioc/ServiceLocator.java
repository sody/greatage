/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import java.util.Set;

/**
 * This class represents object which can provide access to services by their id, or (when appropriate) by just service
 * interface. It is an entry point to greateage IoC container.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceLocator {

	/**
	 * Gets all service ids provided by this service locator.
	 *
	 * @return all service ids or empty set
	 */
	Set<String> getServiceIds();

	/**
	 * Gets service status by specified service id.
	 *
	 * @param id unique service id
	 * @return service status or null if not found
	 */
	ServiceStatus<?> getServiceStatus(String id);

	/**
	 * Gets a service instance by specified service id. It returns the service's proxy that implements the same interface
	 * as the actual service and instantiates the actual service only as needed with all configured method advices.
	 *
	 * @param id		   unique service id, case is sensitive
	 * @param serviceClass service interface
	 * @param <T>          service type
	 * @return service instance that implement specified service interface
	 * @throws RuntimeException if the service is not found, or if an error occurs instantiating it
	 */
	<T> T getService(String id, Class<T> serviceClass);

	/**
	 * Gets a service instance by its service interface. It returns the service's proxy that implements the same interface
	 * as the actual service and instantiates the actual service only as needed with all configured method advices.
	 *
	 * @param serviceClass service interface
	 * @param <T>          service type
	 * @return service instance that implement specified service interface
	 * @throws RuntimeException if there are no ore more than one services found, or if an error occurs instantiating it
	 */
	<T> T getService(Class<T> serviceClass);

	/**
	 * Retrieves services by their service interface. It returns the service's proxies that implements the same interface
	 * as the actual service and instantiates the actual service only as needed with all configured method advices.
	 *
	 * @param serviceClass service interface
	 * @param <T>          service type
	 * @return services that implement specified service interface or empty set
	 * @throws RuntimeException if an error occurs instantiating it
	 */
	<T> Set<T> findServices(Class<T> serviceClass);

}
