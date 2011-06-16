package org.greatage.inject.proxy;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Invocation;

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
