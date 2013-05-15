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

package org.greatage.inject.internal;

import org.greatage.inject.ApplicationException;
import org.greatage.inject.Marker;
import org.greatage.inject.services.Injector;

import java.lang.reflect.Constructor;

/**
 * This class represents abstract implementation of all types of service configuration. Adds auto build functionality
 * for configuration items instantiation.
 *
 * @param <T> service type
 * @param <C> configuration resulting type
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractConfiguration<T, C> {
	private final Marker<T> marker;
	private final Injector injector;

	/**
	 * Creates new instance of service configuration with defined service resources.
	 */
	protected AbstractConfiguration(final Injector injector, final Marker<T> marker) {
		this.marker = marker;
		this.injector = injector;
	}

	/**
	 * Automatically builds new instance of service configuration item by specified item class.
	 *
	 * @param valueClass service configuration item class
	 * @return automatically built new instance of service configuration item
	 */
	protected <V> V newInstance(final Class<? extends V> valueClass) {
		try {
			final Constructor constructor = valueClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(injector, marker, constructor);
			return valueClass.cast(constructor.newInstance(parameters));
		} catch (Exception e) {
			throw new ApplicationException(String.format("Can't create object of class '%s'", valueClass), e);
		}
	}

	/**
	 * Builds resulting service configuration. Used for calculating configuration instance for service construction
	 * method.
	 *
	 * @return resulting service configuration
	 */
	protected abstract C build();
}
