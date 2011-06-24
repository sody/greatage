package org.greatage.inject.internal;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Marker;
import org.greatage.inject.services.Injector;
import org.greatage.inject.services.Module;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.ScopeManager;
import org.greatage.inject.services.ServiceContributor;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.inject.services.ServiceInterceptor;
import org.greatage.inject.services.ServiceResources;
import org.greatage.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceInitializer<T> {
	private final Logger logger = LoggerFactory.getLogger(ServiceInitializer.class);

	private final Injector injector;
	private final ServiceDefinition<T> service;
	private final Marker<T> marker;

	private final List<ServiceContributor<T>> contributors = CollectionUtils.newList();
	private final List<ServiceInterceptor<T>> interceptors = CollectionUtils.newList();

	ServiceInitializer(final Injector injector, final ServiceDefinition<T> service) {
		this.injector = injector;
		this.service = service;

		marker = service.getMarker();
	}

	public ServiceInitializer<T> addContributors(final Module module) {
		this.contributors.addAll(module.getContributors(marker));
		return this;
	}

	public ServiceInitializer<T> addInterceptors(final Module module) {
		this.interceptors.addAll(module.getInterceptors(marker));
		return this;
	}

	public void initialize(final ScopeManager scopeManager) {
		final ServiceAdviceImpl<T> serviceAdvice = new ServiceAdviceImpl<T>(injector, marker);
		final ServiceResources<T> interceptionResources = new Resources(serviceAdvice);
		for (ServiceInterceptor<T> interceptor : interceptors) {
			logger.debug("Interception service (%s) from (%s)", marker, interceptor);
			interceptor.intercept(interceptionResources);
		}
		final Interceptor interceptor = serviceAdvice.build();
		final Builder builder = new Builder();
		scopeManager.register(service.getMarker(), builder, interceptor);
	}

	class Builder implements ObjectBuilder<T> {
		public T build() {
			final BuildResources buildResources = new BuildResources();
			logger.debug("Building service (%s) from (%s)", marker, service);
			return service.build(buildResources);
		}
	}

	class Resources implements ServiceResources<T> {
		private final Object configuration;

		Resources(final Object configuration) {
			this.configuration = configuration;
		}

		public Marker<T> getMarker() {
			return marker;
		}

		public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
			if (resourceClass.isInstance(configuration)) {
				return resourceClass.cast(configuration);
			}
			return injector.inject(marker, resourceClass, annotations);
		}
	}

	class BuildResources implements ServiceResources<T> {

		public Marker<T> getMarker() {
			return marker;
		}

		@SuppressWarnings({"unchecked"})
		public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
			if (Map.class.equals(resourceClass)) {
				final MappedConfigurationImpl configuration = new MappedConfigurationImpl(injector, marker);
				configure(configuration);
				return resourceClass.cast(configuration.build());
			}
			if (List.class.equals(resourceClass)) {
				final OrderedConfigurationImpl configuration = new OrderedConfigurationImpl(injector, marker);
				configure(configuration);
				return resourceClass.cast(configuration.build());
			}
			if (Collection.class.equals(resourceClass)) {
				final ConfigurationImpl configuration = new ConfigurationImpl(injector, marker);
				configure(configuration);
				return resourceClass.cast(configuration.build());
			}
			return injector.inject(marker, resourceClass, annotations);
		}

		/**
		 * Contributes service configuration by triggering configure phase.
		 *
		 * @param configuration correspondent service configuration instance
		 */
		private void configure(final Object configuration) {
			final ServiceResources<T> contributionResources = new Resources(configuration);
			for (ServiceContributor<T> contributor : contributors) {
				logger.debug("Configuring service (%s) from (%s)", service.getMarker(), contributor);
				contributor.contribute(contributionResources);
			}
		}
	}
}

