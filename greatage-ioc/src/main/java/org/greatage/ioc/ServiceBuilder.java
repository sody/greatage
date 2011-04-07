package org.greatage.ioc;

import org.greatage.ioc.inject.Injector;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.util.CollectionUtils;
import org.greatage.util.OrderingUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceBuilder<T> implements ObjectBuilder<T>, ServiceResources<T> {
	private final Injector injector;
	private final ServiceDefinition<T> service;
	private final List<ServiceContributor<T>> contributors;
	private final List<ServiceDecorator<T>> decorators;

	public ServiceBuilder(final Injector injector, final ServiceDefinition<T> service,
						  final List<ServiceContributor<T>> contributors,
						  final List<ServiceDecorator<T>> decorators) {
		this.injector = injector;
		this.service = service;
		this.contributors = OrderingUtils.order(contributors);
		this.decorators = OrderingUtils.order(decorators);
	}

	public Marker<T> getMarker() {
		return service.getMarker();
	}

	public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
		return injector.inject(getMarker(), resourceClass, annotations);
	}

	public Class<T> getObjectClass() {
		return getMarker().getServiceClass();
	}

	public List<Interceptor> getInterceptors() {
		final List<Interceptor> interceptors = CollectionUtils.newList();
		for (ServiceDecorator<T> decorator : decorators) {
			decorator.decorate(this);
		}
		return interceptors;
	}

	public T build() {
		final ServiceResources<T> buildResources = new BuildResources<T>(this, contributors);
		return service.build(buildResources);
	}
}
