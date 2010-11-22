/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import java.util.Collection;
import java.util.List;

/**
 * This class represents module definition that is used to build {@link org.greatage.ioc.ServiceLocator}. By default it
 * is configured by simple class with all needed methods that represent service build, configure, decorate and advice
 * points. It is also may be annotated by {@link org.greatage.ioc.annotations.Dependency} class to define children
 * modules.
 *
 * @author Ivan Khalopik
 * @see org.greatage.ioc.Service
 * @see org.greatage.ioc.Contributor
 * @see org.greatage.ioc.Decorator
 * @see org.greatage.ioc.Interceptor
 * @since 1.0
 */
public interface Module {

	/**
	 * Gets collection of all service definitions of this module.
	 *
	 * @return all service definitions
	 */
	Collection<Service> getServices();

	/**
	 * Gets an ordered list of all service contributor definitions for specified service.
	 *
	 * @param service service definition
	 * @param <T>     type of service
	 * @return ordered list of all service contributor definitions for specified service or empty list
	 */
	<T> List<Contributor<T>> getContributors(Service<T> service);

	/**
	 * Gets an ordered list of all service decorator definitions for specified service.
	 *
	 * @param service service definition
	 * @param <T>     type of service
	 * @return ordered list of all service decorator definitions for specified service or empty list
	 */
	<T> List<Decorator<T>> getDecorators(Service<T> service);

	/**
	 * Gets an ordered list of all service interceptor definitions for specified service.
	 *
	 * @param service service definition
	 * @param <T>     type of service
	 * @return ordered list of all service interceptor definitions for specified service or empty list
	 */
	<T> List<Interceptor<T>> getInterceptors(Service<T> service);

}
