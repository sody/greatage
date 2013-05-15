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

import org.greatage.inject.services.CoerceException;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link org.greatage.inject.services.Coercion} implementation that converts values from string
 * to enum constant.
 *
 * @param <E> enum type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class StringToEnumCoercion<E extends Enum<E>> extends AbstractCoercion<String, E> {
	private final Map<String, E> stringToEnum = CollectionUtils.newMap();

	/**
	 * Creates new coercion instance that converts values from string to enum constant.
	 *
	 * @param targetClass enum class
	 */
	public StringToEnumCoercion(final Class<E> targetClass) {
		super(String.class, targetClass);
		for (E e : getTargetClass().getEnumConstants()) {
			stringToEnum.put(e.name().toLowerCase(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public E coerce(final String source) {
		final String key = source.toLowerCase();
		if (!stringToEnum.containsKey(key)) {
			throw new CoerceException(source, getTargetClass());
		}
		return stringToEnum.get(key);
	}
}
