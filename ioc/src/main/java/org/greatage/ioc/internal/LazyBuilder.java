/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.ioc.services.ProxyFactory;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LazyBuilder<T> implements ObjectBuilder<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;

	LazyBuilder(final ServiceResources<T> resources, final ObjectBuilder<T> builder) {
		this.resources = resources;
		this.builder = builder;
	}

	public Class<T> getObjectClass() {
		return builder.getObjectClass();
	}

	public T build() {
		try {
			final ProxyFactory proxyFactory = resources.getResource(ProxyFactory.class);
			return proxyFactory.createProxy(builder);
		} catch (Exception e) {
			return builder.build();
		}
	}
}
