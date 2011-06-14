package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceContributor;
import org.greatage.ioc.ServiceDefinition;
import org.greatage.ioc.ServiceInterceptor;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.scope.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBuilder<T> implements ObjectBuilder<T> {
	private final Logger logger = LoggerFactory.getLogger(ServiceBuilder.class);

	private final ServiceDefinition<T> service;
	private final List<ServiceContributor<T>> contributors;
	private final List<ServiceInterceptor<T>> interceptors;

	private final Marker<T> marker;
	private final ServiceResources<T> resources;
	private final Scope scope;

	private Interceptor interceptor;

	ServiceBuilder(final ServiceDefinition<T> service,
						  final List<ServiceContributor<T>> contributors,
						  final List<ServiceInterceptor<T>> interceptors,
						  final ServiceResources<T> resources,
						  final Scope scope) {
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

	public Interceptor getInterceptor() {
		return interceptor;
	}

	public T build() {
		return scope.get(marker);
	}

	class InternalBuilder implements ObjectBuilder<T> {
		public Class<T> getObjectClass() {
			return ServiceBuilder.this.getObjectClass();
		}

		public Interceptor getInterceptor() {
			return ServiceBuilder.this.getInterceptor();
		}

		public T build() {
			final ServiceAdviceImpl<T> serviceAdvice = new ServiceAdviceImpl<T>(resources);
			final ServiceResources<T> extraResources = new ExtraResources<T>(resources, serviceAdvice);
			for (ServiceInterceptor<T> interceptor : interceptors) {
				logger.debug("Interception service (%s) from (%s)", marker, interceptor);
				interceptor.intercept(extraResources);
			}
			interceptor = serviceAdvice.build();

			final ServiceResources<T> buildResources = new BuildResources<T>(resources, contributors);
			logger.debug("Building service (%s) from (%s)", marker, service);
			return service.build(buildResources);
		}
	}
}
