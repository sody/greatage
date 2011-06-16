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

package org.greatage.inject;

/**
 * This class represents base application exception that is thrown almost everywhere in the application powered with
 * Great Age IoC container. All application specific exceptions must be extended from this class.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ApplicationException extends RuntimeException {

	/**
	 * Creates new instance of application exception with defined detail message.
	 *
	 * @param message detail message
	 */
	public ApplicationException(final String message) {
		super(message);
	}

	/**
	 * Creates new instance of application exception with defined detail message and cause throwable.
	 *
	 * @param message detail message
	 * @param cause   cause throwable
	 */
	public ApplicationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
