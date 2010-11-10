/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import com.google.inject.internal.cglib.proxy.Enhancer;

import java.util.List;

/**
 * This class represents proxy factory implementation using CGLib library.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CGLibProxyFactory extends AbstractProxyFactory {

	public <T> T createProxy(final ObjectBuilder<T> objectBuilder, final List<MethodAdvice> advices) {
		validate(objectBuilder);

		final Class superClass = objectBuilder.getObjectClass().isInterface() ? Object.class : objectBuilder.getObjectClass();
		final CGLibInvocationHandler<T> handler = new CGLibInvocationHandler<T>(objectBuilder, advices);
		final Object proxy = objectBuilder.getObjectClass().isInterface() ?
				Enhancer.create(superClass, new Class[]{objectBuilder.getObjectClass()}, handler) :
				Enhancer.create(superClass, handler);
		return objectBuilder.getObjectClass().cast(proxy);
	}

}
