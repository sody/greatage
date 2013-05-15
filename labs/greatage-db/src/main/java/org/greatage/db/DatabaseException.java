package org.greatage.db;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DatabaseException extends RuntimeException {

	public DatabaseException(final String message) {
		super(message);
	}

	public DatabaseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DatabaseException(final Throwable cause) {
		super(cause);
	}
}
