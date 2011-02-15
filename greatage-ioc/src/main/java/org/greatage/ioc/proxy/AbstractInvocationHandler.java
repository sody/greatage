/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class represents utility for lazy creation of object from object builder.
 *
 * @param <T> delegate type
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractInvocationHandler<T> {
	private final ObjectBuilder<T> builder;
	private final List<MethodAdvice> advices;

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder
	 * @param advices method advices
	 */
	protected AbstractInvocationHandler(final ObjectBuilder<T> builder, final List<MethodAdvice> advices) {
		assert builder != null;

		this.builder = builder;
		this.advices = advices;
	}

	/**
	 * Gets object instance. If instance is not initialized, creates it from object builder.
	 *
	 * @return lazy initialized object instance
	 */
	protected T getDelegate() {
		return builder.build();
	}

	/**
	 * Invokes specified method with specified parameters on delegate instance
	 *
	 * @param method	 interface method
	 * @param parameters invocation parameters
	 * @return delegated from invocation return object
	 * @throws Throwable if some problems occurs while invoking method
	 */
	protected Object invoke(final Method method, final Object... parameters) throws Throwable {
		final Method realMethod = getDelegate().getClass().getMethod(method.getName(), method.getParameterTypes());
		return createInvocation(realMethod).proceed(parameters);
	}

	/**
	 * Creates new invocation instance for specified method with defined method advices.
	 *
	 * @param method method
	 * @return new invocation instance for specified method with defined method advices
	 */
	private Invocation createInvocation(final Method method) {
		Invocation invocation = new MethodInvocation(getDelegate(), method);
		if (advices != null) {
			for (MethodAdvice advice : advices) {
				invocation = new AdvisedInvocation(invocation, advice);
			}
		}
		return invocation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("builder", builder);
		db.append("advices", advices);
		return db.toString();
	}
}
