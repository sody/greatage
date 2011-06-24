package org.greatage.inject.internal.proxy;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Invocation;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InterceptedInvocationHandler<T> implements InvocationHandler {
	private final ObjectBuilder<T> builder;
	private final Interceptor interceptor;

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder, not null
	 */
	public InterceptedInvocationHandler(final ObjectBuilder<T> builder, final Interceptor interceptor) {
		this.builder = builder;
		this.interceptor = interceptor;
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final Invocation invocation = new InvocationImpl(builder.build(), method);
		return interceptor.invoke(invocation, args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("builder", builder);
		db.append("interceptor", interceptor);
		return db.toString();
	}
}
