/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.util.Ordered;

/**
 * This class represents service interceptor definition that distributively configures service method advices. By
 * default it is configured by module intercept methods annotated with {@link org.greatage.ioc.annotations.Intercept}
 * class.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @see org.greatage.ioc.proxy.ProxyFactory
 * @see org.greatage.ioc.proxy.MethodAdvice
 * @since 1.0
 */
public interface Interceptor<T> extends Ordered {

	/**
	 * Checks if this service interceptor definition supports specified service.
	 *
	 * @param service service definition
	 * @return true if this service interceptor definition supports specified service, false otherwise
	 */
	boolean supports(Service service);

	/**
	 * Configures service method advices using service resource.
	 *
	 * @param resources service resources
	 * @return service method advice
	 * @throws ApplicationException if error occurs while creating method interceptor
	 */
	MethodAdvice intercept(ServiceResources<T> resources);
}
