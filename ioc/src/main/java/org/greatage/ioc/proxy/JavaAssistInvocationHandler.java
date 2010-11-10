/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JavaAssistInvocationHandler<T> extends AbstractInvocationHandler<T> {
	/**
	 * Creates new instance of utility for lazy creation of object from specified object builder.
	 *
	 * @param builder object builder
	 * @param advices method advices
	 */
	public JavaAssistInvocationHandler(final ObjectBuilder<T> builder, final List<MethodAdvice> advices) {
		super(builder, advices);
	}

	@Override
	public T getDelegate() {
		return super.getDelegate();
	}

	@Override
	public Object invoke(final Method method, final Object... parameters) throws Throwable {
		return super.invoke(method, parameters);
	}
}
