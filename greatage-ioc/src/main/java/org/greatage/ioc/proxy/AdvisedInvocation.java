/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AdvisedInvocation implements Invocation {
	private final Invocation delegate;
	private final MethodAdvice advice;

	public AdvisedInvocation(final Invocation delegate, final MethodAdvice advice) {
		this.delegate = delegate;
		this.advice = advice;
	}

	public String getName() {
		return delegate.getName();
	}

	public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
		return delegate.getAnnotation(annotationClass);
	}

	public Class<?> getReturnType() {
		return delegate.getReturnType();
	}

	public Class<?>[] getParameterTypes() {
		return delegate.getParameterTypes();
	}

	public Object proceed(final Object... parameters) throws Throwable {
		return advice.advice(delegate, parameters);
	}
}
