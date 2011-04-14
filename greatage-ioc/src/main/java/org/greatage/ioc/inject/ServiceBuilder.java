package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceContributor;
import org.greatage.ioc.ServiceInterceptor;
import org.greatage.ioc.ServiceDefinition;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.scope.Scope;
import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceBuilder<T> implements ObjectBuilder<T> {
	private final Logger logger;
	private final ServiceDefinition<T> service;
	private final List<ServiceContributor<T>> contributors;
	private final List<ServiceInterceptor<T>> interceptors;

	private final Marker<T> marker;
	private final ServiceResources<T> resources;
	private final Scope scope;

	public ServiceBuilder(final Logger logger,
						  final ServiceDefinition<T> service,
						  final List<ServiceContributor<T>> contributors,
						  final List<ServiceInterceptor<T>> interceptors,
						  final ServiceResources<T> resources,
						  final Scope scope) {
		this.logger = logger;
		this.service = service;
		this.contributors = contributors;
		this.interceptors = interceptors;
		this.resources = resources;
		this.scope = scope;

		marker = service.getMarker();
		scope.put(marker, new InternalBuilder());
	}

	public Class<T> getObjectClass() {
		return marker.getServiceClass();
	}

	public List<Interceptor> getInterceptors() {
		final List<Interceptor> interceptors = CollectionUtils.newList();
		for (ServiceInterceptor<T> interceptor : this.interceptors) {
			logger.debug("Interception service (%s) from (%s)", marker, interceptor);
			interceptor.decorate(resources);
		}
		return interceptors;
	}

	public T build() {
		return scope.get(marker);
	}

	class InternalBuilder implements ObjectBuilder<T> {
		public Class<T> getObjectClass() {
			return ServiceBuilder.this.getObjectClass();
		}

		public List<Interceptor> getInterceptors() {
			return ServiceBuilder.this.getInterceptors();
		}

		public T build() {
			final ServiceResources<T> buildResources = new BuildResources<T>(logger, resources, contributors);
			logger.debug("Building service (%s) from (%s)", marker, service);
			return service.build(buildResources);
		}
	}
}
