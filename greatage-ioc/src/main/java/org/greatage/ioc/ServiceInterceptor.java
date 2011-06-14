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

/**
 * This class represents service interceptor definition that distributively configures service method advices. By default it is
 * configured by module intercept methods annotated with {@link org.greatage.ioc.annotations.Intercept} class.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @see org.greatage.ioc.proxy.ProxyFactory
 * @see org.greatage.ioc.proxy.Interceptor
 * @since 1.0
 */
public interface ServiceInterceptor<T> {

	Marker<T> getMarker();

	/**
	 * Configures service method advices using service resource.
	 *
	 * @param resources service resources
	 * @return service method invoke
	 * @throws ApplicationException if error occurs while creating method interceptor
	 */
	void intercept(ServiceResources<T> resources);
}
