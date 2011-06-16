package org.greatage.inject.internal;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Marker;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.Scope;
import org.greatage.inject.services.ServiceContributor;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.inject.services.ServiceInterceptor;
import org.greatage.inject.services.ServiceResources;
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
