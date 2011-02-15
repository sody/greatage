/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class represents implementation of invocation handler for javassist proxy objects.
 *
 * @param <T> object type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JavassistInvocationHandler<T> extends AbstractInvocationHandler<T> {

	/**
	 * Creates new instance of invocation handler for javassist proxy objects.
	 *
	 * @param builder object builder
	 * @param advices method advices
	 */
	public JavassistInvocationHandler(final ObjectBuilder<T> builder, final List<MethodAdvice> advices) {
		super(builder, advices);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getDelegate() {
		return super.getDelegate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object invoke(final Method method, final Object... parameters) throws Throwable {
		return super.invoke(method, parameters);
	}
}
