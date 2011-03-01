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

package org.greatage.ioc.access;

import org.greatage.ioc.ApplicationException;

/**
 * This class represents exception that is thrown by property access helpers.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class PropertyAccessException extends ApplicationException {

	/**
	 * Creates new instance of property access exception with defined detail message.
	 *
	 * @param message detail message
	 */
	public PropertyAccessException(final String message) {
		super(message);
	}

	/**
	 * Creates new instance of property access exception with defined detail message and cause throwable.
	 *
	 * @param message detail message
	 * @param cause   cause problem
	 */
	public PropertyAccessException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
