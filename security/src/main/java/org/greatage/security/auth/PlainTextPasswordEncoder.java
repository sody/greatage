/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security.auth;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PlainTextPasswordEncoder implements PasswordEncoder {
	public String encode(final String password) {
		return password;
	}
}
