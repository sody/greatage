/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GlobalScope extends AbstractScope {
	private final Map<String, Object> services = CollectionUtils.newConcurrentMap();

	@Override
	protected <E> boolean contains(final ServiceResources<E> resources) {
		return services.containsKey(resources.getServiceId());
	}

	@Override
	protected <E> E get(final ServiceResources<E> resources) {
		final Class<E> serviceClass = resources.getServiceClass();
		return serviceClass.cast(services.get(resources.getServiceId()));
	}

	@Override
	protected <E> void put(final ServiceResources<E> resources, final E service) {
		services.put(resources.getServiceId(), service);
	}
}
