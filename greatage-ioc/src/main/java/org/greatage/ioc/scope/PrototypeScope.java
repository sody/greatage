/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.ObjectBuilder;

/**
 * This class represents {@link Scope} implementation that It is used for services that have different state for all
 * points where it is accessed. Default scope identifier is {@link ScopeConstants#PROTOTYPE}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PrototypeScope implements Scope {

	/**
	 * {@inheritDoc} Always creates new service instance.
	 */
	public <E> E get(final ServiceResources<E> resources, final ObjectBuilder<E> builder) {
		return builder.build();
	}

	/**
	 * {@inheritDoc}
	 */
	public void cleanup() {
		//do nothing
	}
}
