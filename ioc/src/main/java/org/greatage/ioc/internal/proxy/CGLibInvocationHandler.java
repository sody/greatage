/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.proxy;

import com.google.inject.internal.cglib.proxy.InvocationHandler;
import org.greatage.ioc.services.ObjectBuilder;

import java.lang.reflect.Method;

/**
 * This class represents implementation of invocation handler for CGLib proxy objects.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CGLibInvocationHandler<T> extends AbstractInvocationHandler<T> implements InvocationHandler {

	/**
	 * Creates new instance of invocation handler for CGLib proxy objects.
	 *
	 * @param builder object builder
	 */
	CGLibInvocationHandler(final ObjectBuilder<T> builder) {
		super(builder);
	}

	/**
	 * Delegates all method invocations to delegate instance.
	 */
	public Object invoke(final Object obj, final Method method, final Object[] args) throws Throwable {
		return method.invoke(getDelegate(), args);
	}
}
