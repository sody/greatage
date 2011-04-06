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

package org.greatage.ioc.inject;

import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceLocator;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * This class represents default {@link Injector} implementation that is used for retrieving default service resources such as
 * other services, symbols or specific service logger instance.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class DefaultInjector implements Injector {
	private final List<Injector> injectors;
	private final ServiceLocator locator;

	/**
	 * Creates new instance of service initial resources with defined service locator, service identifier, service class and scope.
	 *
	 * @param locator service locator
	 */
	public DefaultInjector(final List<Injector> injectors,
					final ServiceLocator locator) {
		assert injectors != null;
		assert locator != null;

		this.injectors = injectors;
		this.locator = locator;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T inject(final Marker<?> marker, final Class<T> resourceClass, final Annotation... annotations) {
		for (Injector injector : injectors) {
			final T resource = injector.inject(marker, resourceClass, annotations);
			if (resource != null) {
				return resource;
			}
		}

		final Marker<T> resourceMarker = Marker.generate(resourceClass, annotations);
		return locator.getService(resourceMarker);
	}
}
