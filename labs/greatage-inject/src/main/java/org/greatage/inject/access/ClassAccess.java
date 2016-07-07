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

package org.greatage.inject.access;

import java.util.Set;

/**
 * This interface represents helper that simplifies bean's property resolving mechanism. It provides set of methods for
 * resolving all property names, getting and setting property values for specified bean instance.
 *
 * @param <T> bean type
 * @author Ivan Khalopik
 * @see ClassAccessSource
 * @see PropertyAccess
 * @since 1.0
 */
public interface ClassAccess<T> {

	/**
	 * Obtains all property names for configured bean class.
	 *
	 * @return set with all bean property names or empty set
	 */
	Set<String> getPropertyNames();

	/**
	 * Gets bean class.
	 *
	 * @return bean class
	 */
	Class<T> getType();

	/**
	 * Gets property access instance for specified property.
	 *
	 * @param propertyName property name, not null
	 * @return property access instance for specified property or null if not found
	 */
	PropertyAccess<T> getPropertyAccess(String propertyName);

	/**
	 * Gets bean property value for specified property.
	 *
	 * @param instance	 bean instance
	 * @param propertyName property name
	 * @return bean property value
	 * @throws PropertyAccessException if bean has no such read property or error occurs while getting value
	 */
	Object get(T instance, String propertyName);

	/**
	 * Sets bean property value for specified property.
	 *
	 * @param instance	 bean instance
	 * @param propertyName property name
	 * @param value		property value
	 * @throws PropertyAccessException if bean has no such write property or error occurs while setting value
	 */
	void set(T instance, String propertyName, Object value);
}
