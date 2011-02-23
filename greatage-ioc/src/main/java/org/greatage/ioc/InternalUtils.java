/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

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

	/**
	 * Generates service identifier by specified class alias and id. If class alias no equal to void, it class name will be
	 * used as service id or string id otherwise.
	 *
	 * @param alias service class alias
	 * @param id default service id
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
}
