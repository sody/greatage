package org.greatage.ioc.inject;

import org.greatage.ioc.InvocationFilter;
import org.greatage.ioc.ServiceAdviceOptions;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.ioc.proxy.Invocation;
import org.greatage.util.Ordered;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class ServiceAdviceOptionsImpl implements ServiceAdviceOptions, Ordered {
	private static final InvocationFilter DEFAULT_FILTER = new InvocationFilter() {
		public boolean supports(final Invocation invocation) {
			return true;
		}
	};

	private final Interceptor interceptor;

	private final String orderId;
	private final List<String> orderConstraints;

	private InvocationFilter filter = DEFAULT_FILTER;

	ServiceAdviceOptionsImpl(final Interceptor interceptor, final String orderId, final String... orderConstraints) {
		this.interceptor = interceptor;
		this.orderId = orderId;
		this.orderConstraints = Arrays.asList(orderConstraints);
	}

	public String getOrderId() {
		return orderId;
	}

	public List<String> getOrderConstraints() {
		return orderConstraints;
	}

	public InterceptorHolder build() {
		return new InterceptorHolder(interceptor, filter);
	}
}
