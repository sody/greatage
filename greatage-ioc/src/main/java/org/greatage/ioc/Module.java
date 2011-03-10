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

import java.util.Collection;
import java.util.List;

/**
 * This class represents module definition that is used to build {@link org.greatage.ioc.ServiceLocator}. By default it
 * is configured by simple class with all needed methods that represent service build, configure, decorate and advice
 * points. It is also may be annotated by {@link org.greatage.ioc.annotations.Dependency} class to define children
 * modules.
 *
 * @author Ivan Khalopik
 * @see org.greatage.ioc.Service
 * @see org.greatage.ioc.Contributor
 * @see Decorator
 * @since 1.0
 */
public interface Module {

	/**
	 * Gets collection of all service definitions of this module.
	 *
	 * @return all service definitions
	 */
	Collection<Service> getServices();

	/**
	 * Gets an ordered list of all service contributor definitions for specified service.
	 *
	 * @param service service definition
	 * @param <T>     type of service
	 * @return ordered list of all service contributor definitions for specified service or empty list
	 */
	<T> List<Contributor<T>> getContributors(Service<T> service);

	/**
	 * Gets an ordered list of all service interceptor definitions for specified service.
	 *
	 * @param service service definition
	 * @param <T>     type of service
	 * @return ordered list of all service interceptor definitions for specified service or empty list
	 */
	<T> List<Decorator<T>> getDecorators(Service<T> service);
}
