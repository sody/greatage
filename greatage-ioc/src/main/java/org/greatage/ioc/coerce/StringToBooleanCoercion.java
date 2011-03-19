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
 * This class represents {@link Coercion} implementation that converts values from string to boolean.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class StringToBooleanCoercion extends AbstractCoercion<String, Boolean> {
	private static final String VALUE_TRUE = "true";
	private static final String VALUE_FALSE = "false";

	/**
	 * Creates new coercion instance that converts values from string to boolean.
	 */
	public StringToBooleanCoercion() {
		super(String.class, Boolean.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public Boolean coerce(final String source) {
		if (VALUE_TRUE.equalsIgnoreCase(source)) {
			return Boolean.TRUE;
		} else if (VALUE_FALSE.equalsIgnoreCase(source)) {
			return Boolean.FALSE;
		}
		throw new CoerceException(source, getTargetClass());
	}
}
