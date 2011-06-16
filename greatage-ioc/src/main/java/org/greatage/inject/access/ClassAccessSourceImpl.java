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

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents default {@link ClassAccessSource} implementation that gets class access instances by specified
 * class and builds missing.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClassAccessSourceImpl implements ClassAccessSource {
	private final Map<Class, ClassAccess> accesses = CollectionUtils.newConcurrentMap();

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <T> ClassAccess<T> getAccess(final Class<T> clazz) {
		assert clazz != null;

		if (!accesses.containsKey(clazz)) {
			final ClassAccess<T> access = buildClassAccess(clazz);
			accesses.put(clazz, access);
		}
		return accesses.get(clazz);
	}

	/**
	 * Creates new class access instance for specified bean class.
	 *
	 * @param clazz bean class
	 * @param <T>   bean type
	 * @return new class access instance
	 * @throws PropertyAccessException when error occurs while getting class access
	 */
	private <T> ClassAccess<T> buildClassAccess(final Class<T> clazz) {
		return new ClassAccessImpl<T>(clazz);
	}
}
