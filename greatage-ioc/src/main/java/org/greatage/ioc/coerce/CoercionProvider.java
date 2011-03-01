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

package org.greatage.ioc.coerce;

/**
 * This interface represents {@link TypeCoercer} configuration point that provides {@link Coercion} instances by
 * supported source and target coercion classes.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface CoercionProvider {

	/**
	 * Gets coercion instance by specified source and target coercion classes. If there no such coercion this method must
	 * return {@code null}.
	 *
	 * @param sourceClass source class
	 * @param targetClass target class
	 * @param <S>         source type
	 * @param <T>         target type
	 * @return coercion instance or null if not found
	 */
	<S, T> Coercion<S, T> getCoercion(Class<S> sourceClass, Class<T> targetClass);
}
