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

import org.greatage.ioc.annotations.MarkerAnnotation;
import org.greatage.ioc.annotations.Service;
import org.greatage.util.StringUtils;

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

	public static <T> Marker<T> generateMarker(final Class<T> defaultClass, final Annotation... annotations) {
		final Service service = findAnnotation(Service.class, annotations);
		final MarkerAnnotation marker = findAnnotation(MarkerAnnotation.class, annotations);
		if (service != null) {
			final Class serviceClass = void.class.equals(service.service()) ? defaultClass : service.service();
			final Class targetClass = void.class.equals(service.value()) ? serviceClass : service.value();
			//noinspection unchecked
			return new MarkerImpl<T>(serviceClass, targetClass, marker);
		} else {
			//noinspection unchecked
			return new MarkerImpl<T>(defaultClass, defaultClass, marker);
		}
	}

	public static boolean supports(final Marker<?> first, final Marker<?> second) {
		if (first.getTargetClass().isAssignableFrom(second.getTargetClass())) {
			if (first.getAnnotation() != null && first.getAnnotation().equals(second.getAnnotation())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generates service identifier by specified class alias and id. If class alias no equal to void, it class name will be
	 * used as service id or string id otherwise.
	 *
	 * @param alias service class alias
	 * @param id	default service id
	 * @return generated service id or null
	 */
	public static String generateServiceId(final Class alias, final String id) {
		return !Void.class.isAssignableFrom(alias) ? alias.getName() : !StringUtils.isEmpty(id) ? id : null;
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

	private static <A extends Annotation> A findAnnotation(final Class<A> annotationClass, final Annotation... annotations) {
		for (Annotation annotation : annotations) {
			if (annotationClass.isInstance(annotation)) {
				return annotationClass.cast(annotation);
			}
		}
		return null;
	}
}
