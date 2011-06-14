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

package org.greatage.ioc.coerce;

/**
 * This interface represents type coercer service that converts source values to specified target class.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface TypeCoercer {

	/**
	 * Converts source value to specified target class.
	 *
	 * @param input	  source value
	 * @param targetType target class
	 * @param <S>        source type
	 * @param <T>        target type
	 * @return converted source value
	 * @throws CoerceException if can not convert
	 */
	<S, T> T coerce(S input, Class<T> targetType);

	/**
	 * Gets coercion instance that supports coercion from source to target class.
	 *
	 * @param sourceClass source class
	 * @param targetClass target class
	 * @param <S>         source type
	 * @param <T>         target type
	 * @return coercion instance or null if no correspondent coercion found
	 */
	<S, T> Coercion<S, T> getCoercion(Class<S> sourceClass, Class<T> targetClass);
}
