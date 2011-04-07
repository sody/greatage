package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceContributor;
import org.greatage.ioc.ServiceDecorator;
import org.greatage.ioc.ServiceDefinition;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.JdkProxyFactory;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.scope.InternalScope;
import org.greatage.ioc.scope.Scope;
import org.greatage.util.OrderingUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class InternalInjector implements Injector {
	private final ProxyFactory proxyFactory;
	private final Scope scope;

	public InternalInjector() {
		scope = new InternalScope();
		proxyFactory = new JdkProxyFactory();
	}

	public <T> T createService(final ServiceDefinition<T> service,
							   final List<ServiceContributor<T>> contributors,
							   final List<ServiceDecorator<T>> decorators) {
		final InternalServiceResources<T> resources = new InternalServiceResources<T>(service.getMarker());
		final List<ServiceContributor<T>> orderedContributors = OrderingUtils.order(contributors);
		final List<ServiceDecorator<T>> orderedDecorators = OrderingUtils.order(decorators);

		final ServiceBuilder<T> builder =
				new ServiceBuilder<T>(service, orderedContributors, orderedDecorators, resources, scope);
		return proxyFactory.createProxy(builder);
	}

	class InternalServiceResources<T> implements ServiceResources<T> {
		private final Marker<T> marker;

		InternalServiceResources(final Marker<T> marker) {
			this.marker = marker;
		}

		public Marker<T> getMarker() {
			return marker;
		}

		public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
			if (Scope.class.equals(resourceClass)) {
				return resourceClass.cast(scope);
			}
			final Marker<R> resourceMarker = Marker.generate(resourceClass, annotations);
			return scope.get(resourceMarker);
		}
	}
}
