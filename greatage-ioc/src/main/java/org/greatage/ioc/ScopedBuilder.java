/*
 * Copyright 2011 Ivan Khalopik
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

import org.greatage.ioc.proxy.ObjectBuilder;
import org.greatage.ioc.scope.Scope;
import org.greatage.ioc.scope.ScopeManager;

/**
 * This class represents service builder decorator that builds service using service scope.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScopedBuilder<T> implements ObjectBuilder<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;

	private Scope scope;

	/**
	 * Creates new instance of service builder decorator that builds service using service scope with defined service
	 * resources and decorated service builder.
	 *
	 * @param resources service resources
	 * @param builder   decorated service builder
	 */
	ScopedBuilder(final ServiceResources<T> resources, final ObjectBuilder<T> builder) {
		this.resources = resources;
		this.builder = builder;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getObjectClass() {
		return builder.getObjectClass();
	}

	/**
	 * {@inheritDoc} Delegates object creation to service scope.
	 */
	public T build() {
		return getScope().get(resources, builder);
	}

	/**
	 * Gets service scope instance or creates it if it is not created yet.
	 *
	 * @return service scope instance
	 */
	private Scope getScope() {
		if (scope == null) {
			final ScopeManager scopeManager = resources.getResource(ScopeManager.class);
			scope = scopeManager.getScope(resources.getServiceScope());
			if (scope == null) {
				throw new IllegalStateException(
						String.format("Wrong scope specified for service (%s, %s). scope=%s",
								resources.getServiceId(), resources.getServiceClass(), resources.getServiceScope()));
			}
		}
		return scope;
	}
}
