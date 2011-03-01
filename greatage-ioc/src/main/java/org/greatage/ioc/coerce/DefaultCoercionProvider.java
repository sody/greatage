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

import java.util.Collection;

/**
 * This class represents {@link CoercionProvider} that can be configured with multiply {@link Coercion} instances.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class DefaultCoercionProvider implements CoercionProvider {
	private final Collection<Coercion> coercions;

	/**
	 * Creates new coercion provider configured with multiply {@link Coercion} instances.
	 *
	 * @param coercions coercions configuration
	 */
	public DefaultCoercionProvider(final Collection<Coercion> coercions) {
		assert coercions != null;

		this.coercions = coercions;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <S, T> Coercion<S, T> getCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		for (Coercion coercion : coercions) {
			if (supports(coercion, sourceClass, targetClass)) {
				return coercion;
			}
		}
		return null;
	}

	/**
	 * Checks if specified coercion instance supports coercion from source to target class.
	 *
	 * @param coercion	coercion to check
	 * @param sourceClass source class
	 * @param targetClass target class
	 * @return true if specified coercion instance supports coercion from source to target class, false otherwise
	 */
	private boolean supports(final Coercion coercion, final Class sourceClass, final Class targetClass) {
		if (coercion.getSourceClass().isAssignableFrom(sourceClass)) {
			if (targetClass.isAssignableFrom(coercion.getTargetClass())) {
				return true;
			}
		}
		return false;
	}
}
