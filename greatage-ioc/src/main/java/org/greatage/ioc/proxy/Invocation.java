/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
