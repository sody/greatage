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
 * This class represents service binding options that is returned by {@link org.greatage.ioc.ServiceBinder} to define
 * service unique id, service scope and is it overrides the existing service.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ServiceBindingOptions {

	/**
	 * Defines service unique id.
	 *
	 * @param id service unique id
	 * @return this service binding options
	 */
	ServiceBindingOptions withId(String id);

	/**
	 * Defines service alias that represent service unique id as class.
	 *
	 * @param alias service alias
	 * @return this service binding options
	 */
	ServiceBindingOptions withAlias(Class alias);

	/**
	 * Defines service scope.
	 *
	 * @param scope service scope
	 * @return this service binding options
	 */
	ServiceBindingOptions withScope(String scope);

	/**
	 * Defines option that service overrides the existing service definition.
	 *
	 * @return this service binding options
	 */
	ServiceBindingOptions override();
}
