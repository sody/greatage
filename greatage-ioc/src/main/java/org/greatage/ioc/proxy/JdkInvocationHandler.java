/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class represents implementation of invocation handler for JDK proxy objects.
 *
 * @param <T> object type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdkInvocationHandler<T> extends AbstractInvocationHandler<T>
		implements java.lang.reflect.InvocationHandler {

	/**
	 * Creates new instance of invocation handler for JDK proxy objects.
	 *
	 * @param builder object builder
	 * @param advices method advices
	 */
	JdkInvocationHandler(final ObjectBuilder<T> builder, final List<MethodAdvice> advices) {
		super(builder, advices);
	}

	/**
	 * {@inheritDoc} Delegates all method invocations to delegate instance.
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return invoke(method, args);
	}
}
