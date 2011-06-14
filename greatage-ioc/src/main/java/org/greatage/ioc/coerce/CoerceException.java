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

import org.greatage.ioc.ApplicationException;

/**
 * This class represents exception that is thrown by {@link Coercion} implementations if error occurs while converting
 * value.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class CoerceException extends ApplicationException {

	/**
	 * Creates new instance of coerce exception with defined source value and target class.
	 *
	 * @param source	  source value
	 * @param targetClass target class
	 */
	public CoerceException(final Object source, final Class targetClass) {
		this(source, targetClass, null);
	}

	/**
	 * Creates new instance of coerce exception with defined source value, target class and cause throwable.
	 *
	 * @param source	  source value
	 * @param targetClass target class
	 * @param cause	   cause throwable
	 */
	public CoerceException(final Object source, final Class targetClass, final Throwable cause) {
		super(String.format("Can not coerce '%s' to '%s'", source, targetClass), cause);
	}
}
