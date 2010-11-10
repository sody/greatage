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
public class ThreadScope extends AbstractScope {
	private final ThreadLocal<Map<String, Object>> services = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return CollectionUtils.newMap();
		}
	};

	@Override
	protected <E> boolean contains(final ServiceResources<E> resources) {
		return getServices().containsKey(resources.getServiceId());
	}

	@Override
	protected <E> E get(final ServiceResources<E> resources) {
		final Class<E> serviceClass = resources.getServiceClass();
		return serviceClass.cast(getServices().get(resources.getServiceId()));
	}

	@Override
	protected <E> void put(final ServiceResources<E> resources, final E service) {
		getServices().put(resources.getServiceId(), service);
	}

	private Map<String, Object> getServices() {
		return services.get();
	}
}
