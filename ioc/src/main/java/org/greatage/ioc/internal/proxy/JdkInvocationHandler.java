/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.services.ObjectBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * This class represents implementation of invocation handler for JDK proxy objects.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdkInvocationHandler<T> extends AbstractInvocationHandler<T> implements InvocationHandler {

	/**
	 * Creates new instance of invocation handler for JDK proxy objects.
	 *
	 * @param builder object builder
	 */
	JdkInvocationHandler(final ObjectBuilder<T> builder) {
		super(builder);
	}

	/**
	 * Delegates all method invocations to delegate instance.
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return method.invoke(getDelegate(), args);
	}
}
