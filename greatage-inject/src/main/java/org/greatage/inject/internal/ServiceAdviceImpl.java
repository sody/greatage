package org.greatage.inject.internal;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Marker;
import org.greatage.inject.ServiceAdvice;
import org.greatage.inject.ServiceAdviceOptions;
import org.greatage.inject.services.Injector;
import org.greatage.util.CollectionUtils;
import org.greatage.util.OrderingUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceAdviceImpl<T>
		extends AbstractConfiguration<T, Map<Method, List<Interceptor>>>
		implements ServiceAdvice {
	private final List<ServiceAdviceOptionsImpl> advices = CollectionUtils.newList();
	private final Marker<T> marker;

	public ServiceAdviceImpl(final Injector injector, final Marker<T> marker) {
		super(injector, marker);
		this.marker = marker;
	}

	@Override
	protected Map<Method, List<Interceptor>> build() {
		final List<ServiceAdviceOptionsImpl> ordered = OrderingUtils.order(advices);

		final Map<Method, List<Interceptor>> interceptors = CollectionUtils.newMap();
		for (Method method : marker.getServiceClass().getMethods()) {
			for (ServiceAdviceOptionsImpl options : ordered) {
				if (options.supports(method)) {
					if (!interceptors.containsKey(method)) {
						interceptors.put(method, CollectionUtils.<Interceptor>newList());
					}
					interceptors.get(method).add(options.getInterceptor());
				}
			}
		}
		return interceptors;
	}

	public ServiceAdviceOptions add(final Interceptor interceptor,
									final String orderId,
									final String... constraints) {
		final ServiceAdviceOptionsImpl options = new ServiceAdviceOptionsImpl(interceptor, orderId, constraints);
		advices.add(options);
		return options;
	}

	public ServiceAdviceOptions addInstance(final Class<? extends Interceptor> interceptorClass,
											final String orderId,
											final String... constraints) {
		final Interceptor interceptor = newInstance(interceptorClass);
		return add(interceptor, orderId, constraints);
	}
}
