/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.Locker;
import org.greatage.util.OrderingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScopedService<T> implements ServiceStatus<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private final List<Interceptor<T>> interceptors;

	private final Locker locker = new Locker();

	private T serviceInstance;

	ScopedService(final ServiceLocator locator,
				  final Service<T> service,
				  final List<Configurator<T>> configurators,
				  final List<Decorator<T>> decorators,
				  final List<Interceptor<T>> interceptors) {
		this.resources = new ServiceInitialResources<T>(locator, service);
		final ServiceBuilder<T> serviceBuilder = new ServiceBuilder<T>(resources, service, configurators, decorators);
		this.builder = new ScopedBuilder<T>(resources, serviceBuilder);
		this.interceptors = interceptors;
	}

	public String getServiceId() {
		return resources.getServiceId();
	}

	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}

	public String getServiceScope() {
		return resources.getServiceScope();
	}

	public T getService() {
		if (serviceInstance == null) {
			locker.lock();
			final ProxyFactory proxyFactory = resources.getResource(ProxyFactory.class);
			serviceInstance = proxyFactory.createProxy(builder, createAdvices());
		}
		return serviceInstance;
	}

	private List<MethodAdvice> createAdvices() {
		final List<MethodAdvice> advices = new ArrayList<MethodAdvice>();
		final List<Interceptor<T>> ordered = OrderingUtils.order(interceptors);
		for (Interceptor<T> interceptor : ordered) {
			final MethodAdvice advice = interceptor.intercept(resources);
			advices.add(advice);
		}
		return advices;
	}

	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("resources", resources);
		db.append("builder", builder);
		return db.toString();
	}
}
