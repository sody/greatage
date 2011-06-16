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

/**
 * This interface represents helper that simplifies bean's property resolving mechanism. It provides set of methods for
 * resolving all property attributes, such as readable, writable, name, type. It also provides functionality to set and
 * get property value from specified bean instance.
 *
 * @param <T> bean type
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface PropertyAccess<T> {

	/**
	 * Gets class access instance for configured bean.
	 *
	 * @return class access instance, not null
	 */
	ClassAccess<T> getClassAccess();

	/**
	 * Gets property name.
	 *
	 * @return property name, not null
	 */
	String getName();

	/**
	 * Gets property type.
	 *
	 * @return property type, not null
	 */
	Class getType();

	/**
	 * Checks if property has read access.
	 *
	 * @return true if property is readable, false otherwise
	 */
	boolean isReadable();

	/**
	 * Checks if property has write access.
	 *
	 * @return true if property is writable, false otherwise
	 */
	boolean isWritable();

	/**
	 * Gets bean property value.
	 *
	 * @param instance bean instance
	 * @return bean property value
	 * @throws PropertyAccessException if property has no read access or error occurs while getting value
	 */
	Object get(T instance);

	/**
	 * Sets bean property value.
	 *
	 * @param instance bean instance
	 * @param value	property value
	 * @throws PropertyAccessException if property has no write access or error occurs while setting value
	 */
	void set(T instance, Object value);
}
