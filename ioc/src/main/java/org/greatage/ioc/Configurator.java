/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents service configuration definition that distributly configurates service. By default it is
 * configurated by module configurate methods annotated with {@link org.greatage.ioc.annotations.Configure} class.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Configurator<T> {

	/**
	 * Checks if this service configuration definition supports specified service.
	 *
	 * @param service service definition
	 * @return true if this service configuration definition supports specified service, false otherwise
	 */
	boolean supports(Service service);

	/**
	 * Configurates service instance using service resource. They manipulates with three types of service configuration:
	 * {@link org.greatage.ioc.Configuration}, {@link org.greatage.ioc.OrderedConfiguration} and {@link
	 * org.greatage.ioc.MappedConfiguration}.
	 *
	 * @param resources service resources
	 */
	void configure(ServiceResources<T> resources);

}
