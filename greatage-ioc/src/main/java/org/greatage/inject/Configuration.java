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

package org.greatage.inject;

/**
 * This class represent unordered service configuration that is used by service contributors to configure service
 * instance. It is injected to service resources as a collection of items at build time.
 *
 * @param <V> type of configuration items
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Configuration<V> {

	/**
	 * Adds simple item to service configuration.
	 *
	 * @param item item instance
	 * @return this configuration instance
	 */
	Configuration<V> add(V item);

	/**
	 * Instantiates instance of specified class with all injected dependencies and adds this item to service
	 * configuration.
	 *
	 * @param itemClass item class
	 * @return this configuration instance
	 * @throws ApplicationException if error occurs while creating new instance
	 */
	Configuration<V> addInstance(Class<? extends V> itemClass);
}
