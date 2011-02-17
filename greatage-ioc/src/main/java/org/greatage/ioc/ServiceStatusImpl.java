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
 * This class represents implementation of {@link ServiceStatus} that is used by default for all services. It lazily
 * creates, configures, decorates and intercepts service using {@link ProxyFactory} service and scoped builder.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceStatusImpl<T> implements ServiceStatus<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private final List<Interceptor<T>> interceptors;

	private final Locker locker = new Locker();

	private T serviceInstance;

	/**
	 * Creates new instance of service status that is used by default for all services. It lazily creates, configures,
	 * decorates and intercepts service using {@link ProxyFactory} service and scoped builder.
	 *
	 * @param locator	  service locator
	 * @param service	  service definition
	 * @param contributors service contributors
	 * @param decorators   service decorators
	 * @param interceptors service interceptors
	 */
	ServiceStatusImpl(final ServiceLocator locator,
					  final Service<T> service,
					  final List<Contributor<T>> contributors,
					  final List<Decorator<T>> decorators,
					  final List<Interceptor<T>> interceptors) {
		this.resources = new ServiceInitialResources<T>(locator, service);
		final ServiceBuilder<T> serviceBuilder = new ServiceBuilder<T>(resources, service, contributors, decorators);
		this.builder = new ScopedBuilder<T>(resources, serviceBuilder);
		this.interceptors = interceptors;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceId() {
		return resources.getServiceId();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceScope() {
		return resources.getServiceScope();
	}

	/**
	 * {@inheritDoc} Creates service instance using {@link ProxyFactory} service and scoped service builder.
	 */
	public T getService() {
		if (serviceInstance == null) {
			locker.lock();
			final ProxyFactory proxyFactory = resources.getResource(ProxyFactory.class);
			serviceInstance = proxyFactory.createProxy(builder, createAdvices());
		}
		return serviceInstance;
	}

	/**
	 * Creates ordered list of method advices for service using service interceptor definitions.
	 *
	 * @return list of method advices for service or empty list
	 */
	private List<MethodAdvice> createAdvices() {
		final List<MethodAdvice> advices = new ArrayList<MethodAdvice>();
		final List<Interceptor<T>> ordered = OrderingUtils.order(interceptors);
		for (Interceptor<T> interceptor : ordered) {
			final MethodAdvice advice = interceptor.intercept(resources);
			advices.add(advice);
		}
		return advices;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("resources", resources);
		db.append("builder", builder);
		return db.toString();
	}
}