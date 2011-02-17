package org.greatage.ioc;

/**
 * This class represents base application exception that is thrown almost everywhere in the application powered with
 * Great Age IoC container. All application specific exceptions must be extended from this class.
 *
 * @author Ivan Khalopik
 * @since 1.1
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
