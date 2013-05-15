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
import org.greatage.inject.annotations.Singleton;
import org.greatage.inject.services.Scope;
import org.greatage.inject.services.ScopeManager;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * This class represents default {@link ScopeManager} implementation that obtains scope instances by their name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScopeManagerImpl implements ScopeManager {
	private final Set<Marker<?>> services = CollectionUtils.newSet();
	private final Map<Class<? extends Annotation>, Scope> scopes = CollectionUtils.newMap();

	/**
	 * Creates new instance of scope manager with defined mapped configuration of scopes.
	 *
	 * @param scopes scope instances mapped by their identifiers
	 */
	public ScopeManagerImpl(final Collection<Scope> scopes) {
		assert scopes != null;

		for (Scope scope : scopes) {
			this.scopes.put(scope.getKey(), scope);
		}

		assert this.scopes.containsKey(Singleton.class);
	}

	public Set<Marker<?>> getMarkers() {
		return services;
	}

	public <T> Set<T> find(final Marker<T> marker) {
		final Set<T> result = CollectionUtils.newSet();
		for (Marker<?> serviceMarker : services) {
			if (marker.isAssignableFrom(serviceMarker)) {
				final Object service = getScope(marker).get(serviceMarker);
				result.add(marker.getServiceClass().cast(service));
			}
		}
		return result;
	}

	public <T> T get(final Marker<T> marker) {
		return getScope(marker).get(marker);
	}

	public <T> void register(final ServiceBuilder<T> builder) {
		getScope(builder.getMarker()).register(builder);
		services.add(builder.getMarker());
	}

	private <T> Scope getScope(final Marker<T> marker) {
		final Class<? extends Annotation> scopeKey = marker.getScope() != null ? marker.getScope() : Singleton.class;
		final Scope scope = scopes.get(scopeKey);
		if (scope == null) {
			throw new ApplicationException(String.format("Cannot find scope '%s'", scopeKey.getSimpleName()));
		}
		return scope;
	}
}
