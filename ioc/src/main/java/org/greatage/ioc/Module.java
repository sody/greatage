/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Module {

	List<Service> getServices();

	<T> List<Contributor<T>> getConfigurators(Service<T> service);

	<T> List<Decorator<T>> getDecorators(Service<T> service);

	<T> List<Interceptor<T>> getInterceptors(Service<T> service);

}
