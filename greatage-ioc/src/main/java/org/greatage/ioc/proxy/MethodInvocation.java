/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MethodInvocation implements Invocation {
	private final Object instance;
	private final Method method;

	public MethodInvocation(final Object instance, final Method method) {
		this.instance = instance;
		this.method = method;
	}

	public String getName() {
		return method.getName();
	}

	public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
		return method.getAnnotation(annotationClass);
	}

	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	public Class<?>[] getParameterTypes() {
		return method.getParameterTypes();
	}

	public Object proceed(final Object... parameters) throws Throwable {
		return method.invoke(instance, parameters);
	}
}
