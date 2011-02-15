/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.annotation.Annotation;

/**
 * This class represents {@link Invocation} proxy implementation that adds method advices logic to invocation delegate.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AdvisedInvocation implements Invocation {
	private final Invocation delegate;
	private final MethodAdvice advice;

	/**
	 * Creates new instance of invocation proxy that adds method advices logic to invocation delegate.
	 *
	 * @param delegate invocation delegate
	 * @param advice   invocation method advice, can be null
	 */
	public AdvisedInvocation(final Invocation delegate, final MethodAdvice advice) {
		this.delegate = delegate;
		this.advice = advice;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return delegate.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
		return delegate.getAnnotation(annotationClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getReturnType() {
		return delegate.getReturnType();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?>[] getParameterTypes() {
		return delegate.getParameterTypes();
	}

	/**
	 * {@inheritDoc} Adds method advices logic to invocation delegate.
	 */
	public Object proceed(final Object... parameters) throws Throwable {
		return advice.advice(delegate, parameters);
	}
}
