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

package org.greatage.ioc.services;

import org.greatage.ioc.Marker;

/**
 * This class represents service definition that instantiates service. By default it is configured by module build
 * methods annotated with {@link org.greatage.ioc.annotations.Build} class.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceDefinition<T> {

	Marker<T> getMarker();

	/**
	 * Determines if this service definition overrides existing or not. The default value is false.
	 *
	 * @return true if this service definition overrides existing, false otherwise
	 */
	boolean isOverride();

	boolean isEager();

	/**
	 * Builds service instance using configured service resource. They are configured by {@link ServiceContributor}
	 * instances correspondent to this service instance.
	 *
	 * @return service instance, not null
	 * @throws org.greatage.ioc.ApplicationException
	 *          if error occurs while building service
	 */
	T build(ServiceResources<T> resources);
}
