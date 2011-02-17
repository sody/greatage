/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface AuthenticationProvider<T extends Authentication> {

	T signIn(AuthenticationToken token) throws AuthenticationException;

	void signOut(T authentication) throws AuthenticationException;

}