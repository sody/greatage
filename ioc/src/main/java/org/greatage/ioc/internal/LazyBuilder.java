/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal;

import org.greatage.ioc.Interceptor;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.services.MethodAdvice;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.ioc.services.ProxyFactory;
import org.greatage.util.OrderingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LazyBuilder<T> implements ObjectBuilder<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private final List<Interceptor<T>> interceptors;

	LazyBuilder(final ServiceResources<T> resources, final ObjectBuilder<T> builder, final List<Interceptor<T>> interceptors) {
		this.resources = resources;
		this.builder = builder;
		this.interceptors = interceptors;
	}

	public Class<T> getObjectClass() {
		return builder.getObjectClass();
	}

	public T build() {
		try {
			final ProxyFactory proxyFactory = resources.getResource(ProxyFactory.class);
			return proxyFactory.createProxy(builder, createAdvices());
		} catch (Exception e) {
			return builder.build();
		}
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
}
