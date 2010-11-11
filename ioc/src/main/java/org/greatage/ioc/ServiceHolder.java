/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeManager;
import org.greatage.util.DescriptionBuilder;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceHolder<T> implements ServiceStatus<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;

	private Scope scope;

	ServiceHolder(final ServiceResources<T> resources,
				  final ServiceBuilder<T> builder,
				  final List<Interceptor<T>> interceptors) {

		this.resources = resources;
		this.builder = new LazyBuilder<T>(resources, builder, interceptors);
	}

	public T getService() {
		return getServiceScope().get(resources, builder);
	}

	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}

	public Scope getServiceScope() {
		if (scope == null) {
			final ScopeManager scopeManager = resources.getResource(ScopeManager.class);
			scope = scopeManager.getScope(resources.getServiceScope());
			if (scope == null) {
				throw new IllegalStateException(String.format("Wrong scope specified for service (%s, %s). scope=%s", resources.getServiceId(), resources.getServiceClass(), resources.getServiceScope()));
			}
		}
		return scope;
	}

	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("resources", resources);
		db.append("builder", builder);
		return db.toString();
	}
}
