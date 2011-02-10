/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityException extends RuntimeException {
	public SecurityException() {
	}

	public SecurityException(final String message) {
		super(message);
	}

	public SecurityException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SecurityException(final Throwable cause) {
		super(cause);
	}
}
