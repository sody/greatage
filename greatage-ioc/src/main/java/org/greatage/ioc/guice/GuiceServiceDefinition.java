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

package org.greatage.ioc.guice;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Named;
import org.greatage.ioc.ServiceDefinition;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.scope.ScopeConstants;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class GuiceServiceDefinition<T> implements ServiceDefinition<T> {
	private final Injector injector;
	private final Key<T> key;

	private final String serviceId;
	private final Class<T> serviceClass;

	@SuppressWarnings("unchecked")
	GuiceServiceDefinition(final Injector injector, final Key<T> key) {
		this.injector = injector;
		this.key = key;

		serviceId = obtainServiceId(key);
		serviceClass = (Class<T>) key.getTypeLiteral().getType();
	}

	public String getServiceId() {
		return serviceId;
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public boolean isOverride() {
		return false;
	}

	public String getScope() {
		return ScopeConstants.PROTOTYPE;
	}

	public T build(final ServiceResources<T> resources) {
		return injector.getInstance(key);
	}

	private String obtainServiceId(final Key<T> key) {
		final Annotation annotation = key.getAnnotation();
		if (annotation != null && Named.class.isInstance(annotation)) {
			return ((Named) annotation).value();
		}
		return key.getTypeLiteral().getRawType().getName();
	}
}
