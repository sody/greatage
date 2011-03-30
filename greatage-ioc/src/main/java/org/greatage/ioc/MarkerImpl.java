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

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class MarkerImpl<T> implements Marker<T> {
	private final Class<T> serviceClass;
	private final Class<? extends T> targetClass;
	private final Annotation annotation;

	MarkerImpl(final Class<T> serviceClass, final Class<? extends T> targetClass, final Annotation annotation) {
		this.serviceClass = serviceClass;
		this.targetClass = targetClass;
		this.annotation = annotation;
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public Class<? extends T> getTargetClass() {
		return targetClass;
	}

	public Annotation getAnnotation() {
		return annotation;
	}
}
