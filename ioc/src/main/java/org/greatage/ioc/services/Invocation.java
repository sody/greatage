/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.services;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Invocation {

	String getName();

	<T extends Annotation> T getAnnotation(Class<T> annotationClass);

	Class<?> getReturnType();

	Class<?>[] getParameterTypes();

	Object proceed(Object... parameters) throws Throwable;

}
