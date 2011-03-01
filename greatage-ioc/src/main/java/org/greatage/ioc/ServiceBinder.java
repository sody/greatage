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

/**
 * This class represents service bind definition that distributively binds service interfaces to their automatically
 * built instances. It can also specify service unique id, service scope and is it overrides the existing service. By
 * default it is configured by module static bind methods annotated with {@link org.greatage.ioc.annotations.Bind}
 * class.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceBinder {

	/**
	 * Binds specified service interface to its automatically built implementation. Specified implementation class will be
	 * used as its service interface.
	 *
	 * @param implementationClass service implementation class
	 * @param <T>                 type of service
	 * @return service binding options that can be used to define service unique id, service scope and its override option
	 */
	<T> ServiceBindingOptions bind(Class<T> implementationClass);

	/**
	 * Binds specified service interface to its automatically built implementation.
	 *
	 * @param serviceClass		service interface
	 * @param implementationClass service implementation class
	 * @param <T>                 type of service
	 * @return service binding options that can be used to define service unique id, service scope and its override option
	 */
	<T> ServiceBindingOptions bind(Class<T> serviceClass, Class<? extends T> implementationClass);
}
