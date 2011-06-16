package org.greatage.ioc.proxy;

import org.greatage.ioc.Interceptor;
import org.greatage.ioc.Invocation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockInterceptor implements Interceptor {
	private final String message;

	public MockInterceptor(final String message) {
		this.message = message;
	}

	public Object invoke(final Invocation invocation, final Object... parameters) throws Throwable {
		return message + invocation.proceed(parameters);
	}
}
