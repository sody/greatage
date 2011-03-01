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

package org.greatage.ioc.access;

/**
 * This interface represents service that simplifies bean's property resolving mechanism.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface ClassAccessSource {

	/**
	 * Gets class access instance for specified class.
	 *
	 * @param clazz bean class, not null
	 * @param <T>   bean type
	 * @return class access instance for specified class, not null
	 * @throws PropertyAccessException when error occurs while getting class access
	 */
	<T> ClassAccess<T> getAccess(Class<T> clazz);
}
