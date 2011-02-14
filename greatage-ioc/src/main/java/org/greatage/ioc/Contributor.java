/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents service contribution definition that distributively configures services. By default it is
 * configured by module contribute methods annotated with {@link org.greatage.ioc.annotations.Contribute} class.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Contributor<T> {

	/**
	 * Checks if this service contribution definition supports specified service.
	 *
	 * @param service service definition
	 * @return true if this service configuration definition supports specified service, false otherwise
	 */
	boolean supports(Service service);

	/**
	 * Contributes to service configuration using service resource. It manipulates with three types of service
	 * configuration: {@link org.greatage.ioc.Configuration}, {@link org.greatage.ioc.OrderedConfiguration} and {@link
	 * org.greatage.ioc.MappedConfiguration} and provides collection, list and map to service via {@link
	 * org.greatage.ioc.ServiceResources} respectively.
	 *
	 * @param resources service resources
	 */
	void contribute(ServiceResources<T> resources);
}
