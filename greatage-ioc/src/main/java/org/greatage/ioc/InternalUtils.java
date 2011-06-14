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

package org.greatage.ioc;

import org.greatage.ioc.annotations.Qualifier;
import org.greatage.ioc.annotations.Scope;
import org.greatage.ioc.annotations.Singleton;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * This class represents internal utility methods for calculating service dependencies.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InternalUtils {

	public static <T> T findInArray(final Class<T> elementClass, final Object... elements) {
		if (elements != null) {
			for (Object element : elements) {
				if (elementClass.isInstance(element)) {
					return elementClass.cast(element);
				}
			}
		}
		return null;
	}

	public static Class<? extends Annotation> findScope(final Annotation... annotations) {
		final Annotation scope = findAnnotation(Scope.class, annotations);
		return scope != null ? scope.annotationType() : Singleton.class;
	}

	public static <T> Marker<T> generateMarker(final Class<T> serviceClass, final Annotation... annotations) {
		final Annotation qualifier = findAnnotation(Qualifier.class, annotations);
		if (void.class.equals(serviceClass)) {
			//noinspection unchecked
			return (Marker<T>) Marker.get(Object.class, qualifier);
		}

		return Marker.get(serviceClass, qualifier);
	}

	/**
	 * Calculates service dependencies according to specified build constructor.
	 *
	 * @param resources   service resources
	 * @param constructor service build constructor
	 * @param <T>         service type
	 * @return array of calculated service dependencies
	 */
	public static <T> Object[] calculateParameters(final ServiceResources<T> resources, final Constructor constructor) {
		return calculateInjections(resources, constructor.getParameterTypes(), constructor.getParameterAnnotations());
	}

	/**
	 * Calculates service dependencies according to specified build method.
	 *
	 * @param resources service resources
	 * @param method	service build method
	 * @param <T>       service type
	 * @return array of calculated service dependencies
	 */
	public static <T> Object[] calculateParameters(final ServiceResources<T> resources, final Method method) {
		return calculateInjections(resources, method.getParameterTypes(), method.getParameterAnnotations());
	}

	/**
	 * Calculates service dependencies according to specified dependency types and annotations.
	 *
	 * @param resources   service resources
	 * @param types	   dependency types
	 * @param annotations dependency annotations
	 * @param <T>         service type
	 * @return array of calculated service dependencies
	 */
	private static <T> Object[] calculateInjections(final ServiceResources<T> resources,
													final Class<?>[] types, final Annotation[][] annotations) {
		final int count = types.length;
		final Object[] parameters = new Object[count];
		for (int i = 0; i < count; i++) {
			parameters[i] = resources.getResource(types[i], annotations[i]);
		}
		return parameters;
	}

	private static Annotation findAnnotation(final Class<? extends Annotation> annotationClass,
											 final Annotation... annotations) {
		if (annotations != null) {
			for (Annotation annotation : annotations) {
				final Annotation qualifier = findInArray(annotationClass, annotation.annotationType().getAnnotations());
				if (qualifier != null) {
					return annotation;
				}
			}
		}
		return null;
	}
}
