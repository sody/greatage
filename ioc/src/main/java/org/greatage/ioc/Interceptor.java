/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.services.MethodAdvice;
import org.greatage.util.Ordered;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Interceptor<T> extends Ordered {

	boolean supports(Service service);

	MethodAdvice intercept(ServiceResources<T> resources);

}
