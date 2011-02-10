/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.Ordered;

/**
 * This class represents service decorator definition that distributly decorates services. By default it is configured
 * by module decorate methods annotated with {@link org.greatage.ioc.annotations.Decorate} class.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Decorator<T> extends Ordered {

	/**
	 * Checks if this service decorator definition supports specified service.
	 *
	 * @param service service definition
	 * @return true if this service decorator definition supports specified service, false otherwise
	 */
	boolean supports(Service service);

	/**
	 * Decorates service using existing service instance and configured service resource. It retrieves service instance
	 * from service resources by service instance.
	 *
	 * @param resources configured service resources
	 * @return decorated service instance
	 */
	T decorate(ServiceResources<T> resources);

}
