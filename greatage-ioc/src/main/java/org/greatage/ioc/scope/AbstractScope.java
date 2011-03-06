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

package org.greatage.ioc.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.ObjectBuilder;

import java.util.Map;

/**
 * This class represents abstract {@link Scope} implementation that look ups service instance inside the scope and if it
 * is not found creates new instance using specified service builder.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractScope implements Scope {

	/**
	 * {@inheritDoc}
	 */
	public <E> E get(final ServiceResources<E> resources, final ObjectBuilder<E> builder) {
		if (!contains(resources)) {
			final E service = builder.build();
			put(resources, service);
		}
		return get(resources);
	}

	/**
	 * {@inheritDoc}
	 */
	public void cleanup() {
		//TODO: add functionality of closing services, add init method
		getServices().clear();
	}

	/**
	 * Checks are there a service instance inside the scope.
	 *
	 * @param resources service resources
	 * @param <E>       service type
	 * @return true if there are a service instance inside the scope, false otherwise
	 */
	protected <E> boolean contains(final ServiceResources<E> resources) {
		return getServices().containsKey(resources.getServiceId());
	}

	/**
	 * Gets service instance by its resources.
	 *
	 * @param resources service resources
	 * @param <E>       service type
	 * @return service instance
	 */
	protected <E> E get(final ServiceResources<E> resources) {
		final Class<E> serviceClass = resources.getServiceClass();
		return serviceClass.cast(getServices().get(resources.getServiceId()));
	}

	/**
	 * Puts service instance to this scope.
	 *
	 * @param resources service resources
	 * @param service   service instance
	 * @param <E>       service type
	 */
	protected <E> void put(final ServiceResources<E> resources, final E service) {
		getServices().put(resources.getServiceId(), service);
	}

	/**
	 * Gets all service instances contained inside the scope mapped by their identifiers.
	 *
	 * @return all service instances mapped by their identifiers
	 */
	protected abstract Map<String, Object> getServices();
}
