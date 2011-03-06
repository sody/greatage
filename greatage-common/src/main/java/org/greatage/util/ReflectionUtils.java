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

package org.greatage.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents utility methods for working with java reflection.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ReflectionUtils {
	private static final Map<Class, Class> PRIMITIVES_TO_WRAPPERS = new HashMap<Class, Class>();

	static {
		PRIMITIVES_TO_WRAPPERS.put(Boolean.TYPE, Boolean.class);
		PRIMITIVES_TO_WRAPPERS.put(Byte.TYPE, Byte.class);
		PRIMITIVES_TO_WRAPPERS.put(Character.TYPE, Character.class);
		PRIMITIVES_TO_WRAPPERS.put(Double.TYPE, Double.class);
		PRIMITIVES_TO_WRAPPERS.put(Float.TYPE, Float.class);
		PRIMITIVES_TO_WRAPPERS.put(Integer.TYPE, Integer.class);
		PRIMITIVES_TO_WRAPPERS.put(Long.TYPE, Long.class);
		PRIMITIVES_TO_WRAPPERS.put(Short.TYPE, Short.class);
		PRIMITIVES_TO_WRAPPERS.put(Void.TYPE, Void.class);
	}

	/**
	 * Creates new instance of class using constructor with specified parameters.
	 *
	 * @param clazz	  class to create new instance for (not null)
	 * @param parameters constructor parameters
	 * @param <T>        new instance type
	 * @return new instance of class or its implementation if interface
	 */
	public static <T> T newInstance(final Class<T> clazz, final Object... parameters) {
		final Class<?>[] parameterTypes = new Class<?>[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameterTypes[i] = parameters[i] != null ? parameters[i].getClass() : null;
		}
		final List<Constructor<T>> constructors = findConstructors(clazz, parameterTypes);
		Exception lastException = null;
		for (Constructor<T> constructor : constructors) {
			try {
				return constructor.newInstance(parameters);
			} catch (Exception e) {
				lastException = e;
			}
		}
		throw new RuntimeException(String.format("Can't create new instance of class '%s'", clazz), lastException);
	}

	/**
	 * Searches for constructors for class with specified parameter types (null corresponds to any parameter type).
	 *
	 * @param clazz		  object class
	 * @param parameterTypes parameter types, null corresponds to any type
	 * @param <T>            object type
	 * @return list of constructors for class with specified parameter types or empty list if no suitable constructors
	 *         found
	 */
	public static <T> List<Constructor<T>> findConstructors(final Class<T> clazz, final Class<?>... parameterTypes) {
		final List<Constructor<T>> constructors = new ArrayList<Constructor<T>>();
		for (Constructor<?> constructor : clazz.getConstructors()) {
			if (isConstructorSuitable(constructor, parameterTypes)) {
				//noinspection unchecked
				constructors.add((Constructor<T>) constructor);
			}
		}
		return constructors;
	}

	/**
	 * Finds exception with specified class in exception cause.
	 *
	 * @param exception	  exception with cause
	 * @param exceptionClass exception class
	 * @param <T>            exception type
	 * @return exception with specified class retrieved from cause or null if not found
	 */
	public static <T extends Throwable> T findException(final Throwable exception, final Class<T> exceptionClass) {
		Throwable cause = exception;
		while (cause != null) {
			if (exceptionClass.isInstance(cause)) {
				return exceptionClass.cast(cause);
			}
			cause = cause.getCause();
		}
		return null;
	}

	private static boolean isConstructorSuitable(final Constructor<?> constructor, final Class<?>... parameterTypes) {
		final Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
		if (parameterTypes.length != constructorParameterTypes.length) {
			return false;
		}

		for (int i = 0; i < parameterTypes.length; i++) {
			final Class<?> expectedType = parameterTypes[i];
			final Class<?> actualType = constructorParameterTypes[i];
			if (expectedType != null && !actualType.isAssignableFrom(expectedType)) {
				if (!actualType.isPrimitive() ||
						!PRIMITIVES_TO_WRAPPERS.get(actualType).isAssignableFrom(expectedType)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Gets classes from generic type.
	 *
	 * @param genericType generic type
	 * @return array of classes from generic type, not null
	 */
	public static Class<?>[] getClassesFromGenericType(final Type genericType) {
		if (genericType instanceof Class) {
			return new Class[]{Object.class};
		}
		if (!(genericType instanceof ParameterizedType)) {
			return new Class[]{};
		}
		final ParameterizedType parameterizedType = (ParameterizedType) genericType;
		final Type[] types = parameterizedType.getActualTypeArguments();
		final Class[] result = new Class[types.length];
		for (int i = 0; i < types.length; i++) {
			result[i] = types[i] instanceof Class ? (Class) types[i] : null;
		}
		return result;
	}
}
