/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

/**
 * This class represents service definition that instantiates service. By default it is configured by module build
 * methods annotated with {@link org.greatage.ioc.annotations.Build} class.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Service<T> {

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
	 * Determines if this service definition overrides existing or not. The default value is false.
	 *
	 * @return true if this service definition overrides existing, false otherwise
	 */
	boolean isOverride();

	/**
	 * Gets service scope. {@link org.greatage.ioc.scope.ScopeManager} service must be configured to understand this value
	 * of scope.
	 *
	 * @return service scope, not null
	 */
	String getScope();

	/**
	 * Builds service instance using configured service resource. They are configured by {@link
	 * org.greatage.ioc.Contributor} instances correspondent to this service instance.
	 *
	 * @param resources configured service resources
	 * @return service instance, not null
	 */
	T build(ServiceResources<T> resources);
}
