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

package org.greatage.inject.internal.scope;

import org.greatage.inject.ApplicationException;
import org.greatage.inject.Marker;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.Scope;
import org.greatage.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * This class represents abstract {@link Scope} implementation that look ups service instance inside the scope and if it
 * is not found creates new instance using specified service builder.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractScope implements Scope {
	private final Map<Marker, ObjectBuilder> serviceBuilders = CollectionUtils.newConcurrentMap();

	private final Class<? extends Annotation> key;

	protected AbstractScope(final Class<? extends Annotation> key) {
		this.key = key;
	}

	public Class<? extends Annotation> getKey() {
		return key;
	}

	public <S> S get(final Marker<S> marker) {
		if (!serviceBuilders.containsKey(marker)) {
			throw new ApplicationException(
					String.format("Cannot find service (%s) in %s scope", marker, key.getSimpleName()));
		}

		if (!containsService(marker)) {
			@SuppressWarnings("unchecked")
			final ObjectBuilder<S> builder = serviceBuilders.get(marker);
			final S service = builder.build();
			putService(marker, service);
			return service;
		}

		return getService(marker);
	}

	public <S> void put(final Marker<S> marker, final ObjectBuilder<S> builder) {
		serviceBuilders.put(marker, builder);
	}

	/**
	 * {@inheritDoc}
	 */
	public void cleanup() {
		//TODO: add functionality of closing services, add init method
		getScopeServices().clear();
	}

	/**
	 * Checks are there a service instance inside the scope.
	 *
	 * @param marker service resources
	 * @param <S>    service type
	 * @return true if there are a service instance inside the scope, false otherwise
	 */
	protected <S> boolean containsService(final Marker<S> marker) {
		return getScopeServices().containsKey(marker);
	}

	/**
	 * Gets service instance by its resources.
	 *
	 * @param marker service resources
	 * @param <S>    service type
	 * @return service instance
	 */
	protected <S> S getService(final Marker<S> marker) {
		final Class<S> serviceClass = marker.getServiceClass();
		return serviceClass.cast(getScopeServices().get(marker));
	}

	/**
	 * Puts service instance to this scope.
	 *
	 * @param marker  service resources
	 * @param service service instance
	 * @param <S>     service type
	 */
	protected <S> void putService(final Marker<S> marker, final S service) {
		getScopeServices().put(marker, service);
	}

	/**
	 * Gets all service instances contained inside the scope mapped by their identifiers.
	 *
	 * @return all service instances mapped by their identifiers
	 */
	protected abstract Map<Marker, Object> getScopeServices();
}