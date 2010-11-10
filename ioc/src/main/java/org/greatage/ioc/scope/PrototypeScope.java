/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.ObjectBuilder;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PrototypeScope implements Scope {
	public <E> E get(final ServiceResources<E> resources, final ObjectBuilder<E> builder) {
		return builder.build();
	}
}
