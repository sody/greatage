/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.proxy;

import com.google.inject.internal.cglib.proxy.Enhancer;
import org.greatage.ioc.services.ObjectBuilder;

/**
 * This class represents proxy factory implementation using CGLib library.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CGLibProxyFactory extends AbstractProxyFactory {

	public <T> T createProxy(final ObjectBuilder<T> objectBuilder) {
		validate(objectBuilder);

		final Class superClass = objectBuilder.getObjectClass().isInterface() ? Object.class : objectBuilder.getObjectClass();
		final CGLibInvocationHandler<T> handler = new CGLibInvocationHandler<T>(objectBuilder);
		final Object proxy = objectBuilder.getObjectClass().isInterface() ?
				Enhancer.create(superClass, new Class[]{objectBuilder.getObjectClass()}, handler) :
				Enhancer.create(superClass, handler);
		return objectBuilder.getObjectClass().cast(proxy);
	}

}
