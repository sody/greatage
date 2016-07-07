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

package org.greatage.inject.services;

/**
 * This interface represents type converter that converts values from configured source class to target class. It is
 * used as configuration point for {@link TypeCoercer} service.
 *
 * @param <S> source type
 * @param <T> target type
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Coercion<S, T> {

	/**
	 * Gets configured source class.
	 *
	 * @return configured source class, not null
	 */
	Class<S> getSourceClass();

	/**
	 * Gets configured target class.
	 *
	 * @return configured target class, not null
	 */
	Class<T> getTargetClass();

	/**
	 * Converts specified source value to configured target class.
	 *
	 * @param source source value, not null
	 * @return converted to configured target class value
	 * @throws CoerceException when error occurs while converting value
	 */
	T coerce(S source);
}
