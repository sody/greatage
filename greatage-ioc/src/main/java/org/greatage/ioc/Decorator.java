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

import org.greatage.util.Ordered;

/**
 * This class represents service decorator definition that distributively decorates services. By default it is
 * configured by module decorate methods annotated with {@link org.greatage.ioc.annotations.Decorate} class.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Decorator<T> extends Ordered {

	/**
	 * Checks if this service decorator definition supports specified service.
	 *
	 * @param service service definition
	 * @return true if this service decorator definition supports specified service, false otherwise
	 */
	boolean supports(Service service);

	/**
	 * Decorates service using existing service instance and configured service resource. It retrieves service instance
	 * from service resources by service instance.
	 *
	 * @param resources configured service resources
	 * @return decorated service instance
	 * @throws ApplicationException if error occurs while decorating service
	 */
	T decorate(ServiceResources<T> resources);
}
