/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.proxy;

import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Proxy;

/**
 * This class represents proxy factory implementation using JDK.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdkProxyFactory extends AbstractProxyFactory {
	private final ClassLoader classLoader;

	/**
	 * Creates new instance of JDK proxy factory with system class loader.
	 */
	public JdkProxyFactory() {
		this(ClassLoader.getSystemClassLoader());
	}

	/**
	 * Creates new instance of JDK proxy factory with specified class loader.
	 *
	 * @param classLoader class loader
	 */
	public JdkProxyFactory(final ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public <T> T createProxy(final ObjectBuilder<T> builder) {
		validate(builder);

		final Object proxyInstance = Proxy.newProxyInstance(classLoader,
				new Class<?>[]{builder.getObjectClass()},
				new JdkInvocationHandler<T>(builder));
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
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("classLoader", classLoader);
		return builder.toString();
	}
}
