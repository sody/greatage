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

package org.greatage.ioc.internal.coerce;

import org.greatage.ioc.services.CoerceException;

/**
 * This class represents {@link org.greatage.ioc.services.Coercion} implementation that converts values from string to double.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class StringToDoubleCoercion extends AbstractCoercion<String, Double> {

	/**
	 * Creates new coercion instance that converts values from string to double.
	 */
	public StringToDoubleCoercion() {
		super(String.class, Double.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Double coerce(final String source) {
		try {
			return Double.parseDouble(source);
		} catch (NumberFormatException e) {
			throw new CoerceException(source, getTargetClass(), e);
		}
	}
}
