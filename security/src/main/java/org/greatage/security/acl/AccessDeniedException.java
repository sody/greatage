/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security.acl;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AccessDeniedException extends SecurityException {

	public AccessDeniedException() {
	}

	public AccessDeniedException(final String s) {
		super(s);
	}

	public AccessDeniedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(final Throwable cause) {
		super(cause);
	}
}
