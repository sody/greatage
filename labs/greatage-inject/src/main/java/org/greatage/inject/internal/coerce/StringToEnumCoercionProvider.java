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

package org.greatage.inject.internal.coerce;

import org.greatage.inject.services.Coercion;
import org.greatage.inject.services.CoercionProvider;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link CoercionProvider} implementation that provides coercions for string to enum constant
 * coercions.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class StringToEnumCoercionProvider implements CoercionProvider {
	private final Map<Class, Coercion> coercionsByTargetClass = CollectionUtils.newConcurrentMap();

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <S, T> Coercion<S, T> getCoercion(final Class<S> sourceClass, final Class<T> targetClass) {
		if (String.class.isAssignableFrom(sourceClass) && Enum.class.isAssignableFrom(targetClass)) {
			if (!coercionsByTargetClass.containsKey(targetClass)) {
				coercionsByTargetClass.put(targetClass, new StringToEnumCoercion(targetClass));
			}
			return coercionsByTargetClass.get(targetClass);
		}
		return null;
	}
}
