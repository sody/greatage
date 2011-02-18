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
