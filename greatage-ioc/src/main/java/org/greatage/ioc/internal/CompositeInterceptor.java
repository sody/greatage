package org.greatage.ioc.internal;

import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.Invocation;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CompositeInterceptor implements Interceptor {
	private final Map<Invocation, Invocation> invocations = CollectionUtils.newMap();

	private final List<InterceptorHolder> interceptors;

	CompositeInterceptor(final List<InterceptorHolder> interceptors) {
		this.interceptors = interceptors;
	}

	public Object invoke(final Invocation invocation, final Object... parameters) throws Throwable {
		return getInvocation(invocation).proceed(parameters);
	}

	private Invocation getInvocation(final Invocation invocation) {
		if (!invocations.containsKey(invocation)) {
			Invocation interceptedInvocation = invocation;
			for (InterceptorHolder holder : interceptors) {
				if (holder.supports(invocation)) {
					interceptedInvocation = new InterceptedInvocation(interceptedInvocation, holder.getInterceptor());
				}
			}
			invocations.put(invocation, interceptedInvocation);
		}
		return invocations.get(invocation);
	}
}
