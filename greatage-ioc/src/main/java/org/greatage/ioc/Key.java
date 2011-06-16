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

import org.greatage.ioc.annotations.NamedImpl;
import org.greatage.util.AnnotationFactory;
import org.greatage.util.DescriptionBuilder;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Key<T> implements Marker<T> {
	private final Class<T> serviceClass;

	private Class<? extends Annotation> scope;
	private Annotation qualifier;

	private Key(final Class<T> serviceClass) {
		this(serviceClass, null, null);
	}

	private Key(final Class<T> serviceClass, final Class<? extends Annotation> scope, final Annotation qualifier) {
		if (serviceClass == null || serviceClass.equals(void.class)) {
			throw new IllegalArgumentException("Service class should not be null");
		}

		this.serviceClass = serviceClass;
		this.scope = scope;
		this.qualifier = qualifier;
	}

	public Key<T> scoped(final Class<? extends Annotation> scope) {
		this.scope = scope;
		return this;
	}

	public Key<T> qualified(final Annotation qualifier) {
		this.qualifier = qualifier;
		return this;
	}

	public Key<T> qualified(final Class<? extends Annotation> qualifierClass) {
		return qualified(AnnotationFactory.create(qualifierClass));
	}

	public Key<T> named(final String name) {
		return qualified(new NamedImpl(name));
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public Class<? extends Annotation> getScope() {
		return scope;
	}

	public Annotation getQualifier() {
		return qualifier;
	}

	public boolean isAssignableFrom(final Marker<?> marker) {
		if (!serviceClass.isAssignableFrom(marker.getServiceClass())) {
			return false;
		} else if (qualifier != null && !qualifier.equals(marker.getQualifier())) {
			return false;
		}
		//todo: check scope also?
		return true;
	}

	@Override
	public int hashCode() {
		//todo: check scope also?
		return 31 * serviceClass.hashCode() + (qualifier != null ? qualifier.hashCode() : 0);
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
			if (qualifier == null && marker.getQualifier() == null) {
				return true;
			}
			return qualifier != null && qualifier.equals(marker.getQualifier());
		}
		//todo: check scope also?
		return false;
	}

	public static <T> Key<T> get(final Class<T> serviceClass) {
		return new Key<T>(serviceClass);
	}

	public static <T> Key<T> get(final Marker<T> marker) {
		return new Key<T>(marker.getServiceClass(), marker.getScope(), marker.getQualifier());
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("service", serviceClass.getSimpleName());
		if (scope != null) {
			builder.append("scope", scope.getSimpleName());
		}
		if (qualifier != null) {
			builder.append("qualifier", qualifier);
		}
		return builder.toString();
	}
}
