package org.greatage.ioc.inject;

import org.greatage.ioc.InvocationFilter;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.Invocation;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class InterceptorHolder implements InvocationFilter {
	private final Interceptor interceptor;
	private final InvocationFilter filter;

	InterceptorHolder(final Interceptor interceptor,
					  final InvocationFilter filter) {
		this.interceptor = interceptor;
		this.filter = filter;
	}

	public boolean supports(final Invocation invocation) {
		return filter.supports(invocation);
	}

	public Interceptor getInterceptor() {
		return interceptor;
	}
}
