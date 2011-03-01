/*
 * Copyright 2011 Ivan Khalopik
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

import java.util.Set;

/**
 * This class represents object which can provide access to services by their id, or (when appropriate) by just service
 * interface. It is an entry point to Great Age IoC container.
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
	 * @throws ApplicationException if the service is not found, or if an error occurs instantiating it
	 */
	<T> T getService(String id, Class<T> serviceClass);

	/**
	 * Gets a service instance by its service interface. It returns the service's proxy that implements the same interface
	 * as the actual service and instantiates the actual service only as needed with all configured method advices.
	 *
	 * @param serviceClass service interface
	 * @param <T>          service type
	 * @return service instance that implement specified service interface
	 * @throws ApplicationException if there are no ore more than one services found, or if an error occurs instantiating
	 *                              it
	 */
	<T> T getService(Class<T> serviceClass);

	/**
	 * Retrieves services by their service interface. It returns the service's proxies that implements the same interface
	 * as the actual service and instantiates the actual service only as needed with all configured method advices.
	 *
	 * @param serviceClass service interface
	 * @param <T>          service type
	 * @return services that implement specified service interface or empty set
	 * @throws ApplicationException if an error occurs instantiating it
	 */
	<T> Set<T> findServices(Class<T> serviceClass);
}
