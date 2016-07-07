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

package org.greatage.inject.guice;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;
import org.greatage.inject.Marker;
import org.greatage.inject.annotations.Prototype;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.inject.services.ServiceResources;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GuiceServiceDefinition<T> implements ServiceDefinition<T> {
	private final Injector injector;
	private final Key<T> key;
	private final Marker<T> marker;

	@SuppressWarnings("unchecked")
	GuiceServiceDefinition(final Injector injector, final Key<T> key) {
		this.injector = injector;
		this.key = key;
		marker = obtainMarker(key);
	}

	public Marker<T> getMarker() {
		return marker;
	}

	public boolean isOverride() {
		return false;
	}

	public boolean isEager() {
		return true;
	}

	public T build(final ServiceResources<T> resources) {
		return injector.getInstance(key);
	}

	private Marker<T> obtainMarker(final Key<T> key) {
		@SuppressWarnings("unchecked")
		final Class<T> serviceClass = (Class<T>) key.getTypeLiteral().getType();
		final Annotation annotation = key.getAnnotation();
		if (annotation instanceof Named) {
			return org.greatage.inject.Key.get(serviceClass)
					.named(((Named) annotation).value())
					.scoped(Prototype.class);
		}
		return org.greatage.inject.Key.get(serviceClass)
				.qualified(annotation)
				.scoped(Prototype.class);
	}
}
