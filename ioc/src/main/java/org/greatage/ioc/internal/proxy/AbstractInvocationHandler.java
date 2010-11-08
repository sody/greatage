/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.services.Invocation;
import org.greatage.ioc.services.MethodAdvice;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.CollectionUtils;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.Locker;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class represents utility for lazy creation of object from object builder.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractInvocationHandler<T> {
	private final ObjectBuilder<T> builder;
	private final List<MethodAdvice> advices;
	private final Locker locker = new Locker();
	private T delegate;

	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder
	 * @param advices method advices
	 */
	protected AbstractInvocationHandler(final ObjectBuilder<T> builder, final List<MethodAdvice> advices) {
		this.builder = builder;
		this.advices = CollectionUtils.isEmpty(advices) ? null : advices;
	}

	/**
	 * Gets object instance. If instance is not initialized, creates it from object builder.
	 *
	 * @return lazy initialized object instance
	 */
	protected T getDelegate() {
		if (delegate == null) {
			// locks lazy creation to prevent multy building
			locker.lock();
			delegate = builder.build();
		}
		return delegate;
	}

	protected Object invoke(final Method method, final Object... parameters) throws Throwable {
		if (advices != null) {
			final Method realMethod = getDelegate().getClass().getMethod(method.getName(), method.getParameterTypes());
			return getInvocation(realMethod).proceed(parameters);
		}
		return method.invoke(getDelegate(), parameters);
	}

	private Invocation getInvocation(final Method method) {
		Invocation invocation = new MethodInvocation(getDelegate(), method);
		for (MethodAdvice advice : advices) {
			invocation = new AdvisedInvocation(invocation, advice);
		}
		return invocation;
	}

	@Override
	public String toString() {
		final DescriptionBuilder db = new DescriptionBuilder(getClass());
		db.append("builder", builder);
		db.append("delegate", delegate);
		return db.toString();
	}
}
