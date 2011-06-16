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

package org.greatage.inject.internal;

import org.greatage.inject.Marker;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.Scope;
import org.greatage.inject.services.ScopeManager;
import org.greatage.inject.services.InjectionProvider;
import org.greatage.inject.services.Injector;
import org.greatage.inject.services.ServiceContributor;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.inject.services.ServiceInterceptor;
import org.greatage.inject.services.ServiceResources;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This class represents default {@link Injector} implementation that is used for retrieving default service resources
 * such as other services, symbols or specific service logger instance.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultInjector implements Injector {
	private final List<InjectionProvider> providers;
	private final ProxyFactory proxyFactory;
	private final ScopeManager scopeManager;

	public DefaultInjector(final List<InjectionProvider> providers,
						   final ProxyFactory proxyFactory,
						   final ScopeManager scopeManager) {
		assert providers != null;
		assert proxyFactory != null;
		assert scopeManager != null;

		this.providers = providers;
		this.proxyFactory = proxyFactory;
		this.scopeManager = scopeManager;
	}

	public <T> T createService(final ServiceDefinition<T> service,
							   final List<ServiceContributor<T>> contributors,
							   final List<ServiceInterceptor<T>> interceptors) {

		final Marker<T> marker = service.getMarker();
		final DefaultServiceResources<T> resources = new DefaultServiceResources<T>(marker);
		final Scope scope = scopeManager.getScope(marker.getScope());

		final ServiceBuilder<T> builder = new ServiceBuilder<T>(service, contributors, interceptors, resources, scope);
		return service.isEager() ? builder.build() : proxyFactory.createProxy(builder);
	}

	class DefaultServiceResources<T> implements ServiceResources<T> {
		private final Marker<T> marker;

		DefaultServiceResources(final Marker<T> marker) {
			this.marker = marker;
		}

		public Marker<T> getMarker() {
			return marker;
		}

		public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
			for (InjectionProvider provider : providers) {
				final R resource = provider.inject(marker, resourceClass, annotations);
				if (resource != null) {
					return resource;
				}
			}
			return null;
		}
	}
}
