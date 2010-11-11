/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * This class represents proxy factory implementation using JDK.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdkProxyFactory extends AbstractProxyFactory {

	public <T> T createProxy(final ObjectBuilder<T> builder, final List<MethodAdvice> advices) {
		validate(builder);

		final ClassLoader classLoader = builder.getObjectClass().getClassLoader();

		final Object proxyInstance = Proxy.newProxyInstance(classLoader,
				new Class<?>[]{builder.getObjectClass()},
				new JdkInvocationHandler<T>(builder, advices));
		return builder.getObjectClass().cast(proxyInstance);
	}

	/**
	 * Checks if proxy instance can be created for specified class. It can not be created when original class is not
	 * interface.
	 *
	 * @param builder object builder
	 * @throws IllegalArgumentException if proxy instance can not be created
	 */
	@Override
	protected <T> void validate(final ObjectBuilder<T> builder) {
		super.validate(builder);
		if (!builder.getObjectClass().isInterface()) {
			throw new IllegalArgumentException("Proxy class must be an interface");
		}
	}

	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
