package org.greatage.ioc.coerce;

import org.greatage.ioc.ApplicationException;

/**
 * This class represents exception that is thrown by {@link Coercion} implementations if error occurs while converting
 * value.
 *
 * @author Ivan Khalopik
 * @since 1.1
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
