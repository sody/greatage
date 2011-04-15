package org.greatage.ioc.proxy;

/**
 * @author Ivan Khalopik
 * @since 1.1
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
