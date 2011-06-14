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

import org.greatage.util.AnnotationFactory;
import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.util.DescriptionBuilder;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Marker<T> {
	private final Class<T> serviceClass;
	private final Annotation annotation;

	private final int hashCode;

	Marker(final Class<T> serviceClass, final Annotation annotation) {
		if (serviceClass == null || serviceClass.equals(void.class)) {
			throw new IllegalArgumentException("Service class should not be null");
		}

		this.serviceClass = serviceClass;
		this.annotation = annotation;

		this.hashCode = 31 * serviceClass.hashCode() + (annotation != null ? annotation.hashCode() : 0);
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public boolean isAssignableFrom(final Marker<?> marker) {
		if (!serviceClass.isAssignableFrom(marker.getServiceClass())) {
			return false;
		}
		else if (annotation != null && !annotation.equals(marker.getAnnotation())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@SuppressWarnings("SimplifiableIfStatement")
	@Override
	public boolean equals(final Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Marker)) {
			return false;
		}
		final Marker<?> marker = (Marker<?>) object;
		if (serviceClass.equals(marker.getServiceClass())) {
			if (annotation == null && marker.getAnnotation() == null) {
				return true;
			}
			return annotation != null && annotation.equals(marker.getAnnotation());
		}
		return false;
	}

	public static <T> Marker<T> get(final Class<T> serviceClass) {
		return new Marker<T>(serviceClass, null);
	}

	public static <T> Marker<T> get(final Class<T> serviceClass, final Annotation annotation) {
		return new Marker<T>(serviceClass, annotation);
	}

	public static <T> Marker<T> get(final Class<T> serviceClass, final Class<? extends Annotation> annotationClass) {
		final Annotation annotation = AnnotationFactory.create(annotationClass);
		return new Marker<T>(serviceClass, annotation);
	}

	public static <T> Marker<T> get(final Class<T> serviceClass, final String name) {
		return new Marker<T>(serviceClass, new NamedImpl(name));
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("service", serviceClass.getSimpleName());
		builder.append("annotation", annotation);
		return builder.toString();
	}
}
