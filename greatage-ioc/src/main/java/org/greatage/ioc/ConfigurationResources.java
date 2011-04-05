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
 * This class represents service resources proxy that is used during service configure phase and adds service configuration as
 * additional resource.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ConfigurationResources<T> implements ServiceResources<T> {
	private final ServiceResources<T> resources;
	private final Object configuration;

	/**
	 * Creates new instance of service resources proxy for configure phase with defined service resources delegate and service
	 * configuration.
	 *
	 * @param configuration service configuration
	 */
	ConfigurationResources(final ServiceResources<T> resources, final Object configuration) {
		this.resources = resources;
		this.configuration = configuration;
	}

	public Marker<T> getMarker() {
		return resources.getMarker();
	}

	public <R> R getResource(final Class<R> resourceClass, final Annotation... annotations) {
		if (resourceClass.isInstance(configuration)) {
			return resourceClass.cast(configuration);
		}
		return resources.getResource(resourceClass, annotations);
	}
}
