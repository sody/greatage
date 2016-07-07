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

package org.greatage.inject.services;

import org.greatage.inject.Marker;

import java.util.Collection;
import java.util.List;

/**
 * This class represents module definition that is used to build {@link org.greatage.inject.ServiceLocator}. By default
 * it is configured by simple class with all needed methods that represent service build, configure, intercept and
 * invoke points. It is also may be annotated by {@link org.greatage.inject.annotations.Dependency} class to define
 * children modules.
 *
 * @author Ivan Khalopik
 * @see ServiceDefinition
 * @see ServiceContributor
 * @see ServiceInterceptor
 * @since 1.0
 */
public interface Module {

	/**
	 * Gets collection of all service definitions of this module.
	 *
	 * @return all service definitions
	 */
	Collection<ServiceDefinition<?>> getDefinitions();

	/**
	 * Gets an ordered list of all service contributor definitions for specified service.
	 *
	 * @param marker service marker
	 * @param <T>    type of service
	 * @return ordered list of all service contributor definitions for specified service or empty list
	 */
	<T> List<ServiceContributor<T>> getContributors(Marker<T> marker);

	/**
	 * Gets an ordered list of all service interceptor definitions for specified service.
	 *
	 * @param marker service marker
	 * @param <T>    type of service
	 * @return ordered list of all service interceptor definitions for specified service or empty list
	 */
	<T> List<ServiceInterceptor<T>> getInterceptors(Marker<T> marker);
}
