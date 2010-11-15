/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserContextImpl<U extends Authentication> implements UserContext<U> {
	private U user;

	public U getUser() {
		return user;
	}

	public void setUser(final U user) {
		this.user = user;
	}
}
