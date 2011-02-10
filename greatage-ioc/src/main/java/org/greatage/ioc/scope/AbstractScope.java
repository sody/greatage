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
public abstract class AbstractScope implements Scope {

	public <E> E get(final ServiceResources<E> resources, final ObjectBuilder<E> builder) {
		if (!contains(resources)) {
			final E service = builder.build();
			put(resources, service);
		}
		return get(resources);
	}

	public void cleanup() {
		//todo: add functionality of closing services
	}

	protected abstract <E> boolean contains(final ServiceResources<E> resources);

	protected abstract <E> E get(final ServiceResources<E> resources);

	protected abstract <E> void put(final ServiceResources<E> resources, E service);
}
