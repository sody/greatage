/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.ObjectBuilder;

/**
 * This interface represents service scope object that is used to obtain service instances according to their scope.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Scope {

	/**
	 * Gets service by its resource. If service is missed in this scope new instance will be built via specified builder.
	 *
	 * @param resources service resources that identifies the service
	 * @param builder   service builder
	 * @param <E>       type of service
	 * @return service instance bound to this scope
	 */
	<E> E get(ServiceResources<E> resources, ObjectBuilder<E> builder);

	/**
	 * Close scope before end of lifetime.
	 */
	void cleanup();
}
