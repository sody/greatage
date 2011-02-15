/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.proxy;

import java.lang.annotation.Annotation;

/**
 * This class represent method invocation in environment of {@link org.greatage.ioc.proxy.MethodAdvice}. It provides
 * information about method name, its signature and annotations.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Invocation {

	/**
	 * Gets method name.
	 *
	 * @return method name
	 */
	String getName();

	/**
	 * Gets method annotation of specified annotation class if presents.
	 *
	 * @param annotationClass annotation class
	 * @param <T>             type of annotation
	 * @return method annotation of specified annotation class or null if missed
	 */
	<T extends Annotation> T getAnnotation(Class<T> annotationClass);

	/**
	 * Gets method return type.
	 *
	 * @return method return type
	 */
	Class<?> getReturnType();

	/**
	 * Gets method parameter types.
	 *
	 * @return method parameter types
	 */
	Class<?>[] getParameterTypes();

	/**
	 * Proceeds an underlying method invocation with specified parameters.
	 *
	 * @param parameters method invocation parameters
	 * @return method invocation return result
	 * @throws Throwable when error occurs during invocation
	 */
	Object proceed(Object... parameters) throws Throwable;
}
