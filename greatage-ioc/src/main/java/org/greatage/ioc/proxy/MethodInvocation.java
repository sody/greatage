/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * This class represents {@link Invocation} implementation that is based on configured instance method invocation.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MethodInvocation implements Invocation {
	private final Object instance;
	private final Method method;

	/**
	 * Creates new instance of method invocation with defined object instance and method.
	 *
	 * @param instance object instance
	 * @param method   method
	 */
	public MethodInvocation(final Object instance, final Method method) {
		this.instance = instance;
		this.method = method;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return method.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
		return method.getAnnotation(annotationClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?>[] getParameterTypes() {
		return method.getParameterTypes();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object proceed(final Object... parameters) throws Throwable {
		return method.invoke(instance, parameters);
	}
}
