package org.greatage.inject.internal;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Marker;
import org.greatage.inject.ServiceAdvice;
import org.greatage.inject.ServiceAdviceOptions;
import org.greatage.inject.services.Injector;
import org.greatage.util.CollectionUtils;
import org.greatage.util.OrderingUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceAdviceImpl<T> extends AbstractConfiguration<T, Interceptor> implements ServiceAdvice {
	private final List<ServiceAdviceOptionsImpl> advices = CollectionUtils.newList();

	public ServiceAdviceImpl(final Injector injector, final Marker<T> marker) {
		super(injector, marker);
	}

	@Override
	protected Interceptor build() {
		final List<ServiceAdviceOptionsImpl> ordered = OrderingUtils.order(advices);
		final List<InterceptorHolder> interceptors = CollectionUtils.newList();
		for (ServiceAdviceOptionsImpl options : ordered) {
			interceptors.add(options.build());
		}
		return new CompositeInterceptor(interceptors);
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
