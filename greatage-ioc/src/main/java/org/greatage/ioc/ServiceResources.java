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
 * This class represents service resources that may be provided to a service when it initializes, are configurated or
 * are decorated. It also provides access to other services.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceResources<T> {

	/**
	 * Gets unique service id used to locate the service object.
	 *
	 * @return unique service id, not null
	 */
	String getServiceId();

	/**
	 * Gets service interface that both service implementation and service proxy will implement.
	 *
	 * @return service interface, not null
	 */
	Class<T> getServiceClass();

	/**
	 * Gets service scope. {@link org.greatage.ioc.scope.ScopeManager} service must be configured to understand this value
	 * of scope.
	 *
	 * @return service scope, not null
	 */
	String getServiceScope();

	/**
	 * Gets resource to service by its type and annotations used. It can provide logger for service, injected symbols
	 * configured by {@link org.greatage.ioc.symbol.SymbolSource} (needs {@link org.greatage.ioc.annotations.Symbol}
	 * annotation), injected services by id (needs {@link org.greatage.ioc.annotations.Inject} annotation) or services by
	 * their interfaces by default.
	 *
	 * @param resourceClass resource class
	 * @param annotations   resource annotation
	 * @param <E>           resource type
	 * @return requested resource that implements specified class
	 * @throws ApplicationException if an error occurs instantiating resource
	 */
	<E> E getResource(Class<E> resourceClass, Annotation... annotations);
}
