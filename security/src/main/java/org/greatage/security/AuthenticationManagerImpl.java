/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthenticationManagerImpl<T extends Authentication> implements AuthenticationManager {
	private final List<AuthenticationProvider<T>> providers;
	private final SecurityContext<T> securityContext;

	public AuthenticationManagerImpl(final List<AuthenticationProvider<T>> providers, final SecurityContext<T> securityContext) {
		this.providers = providers;
		this.securityContext = securityContext;
	}

	public void signIn(final AuthenticationToken token) throws AuthenticationException {
		final T user = doSignIn(token);
		securityContext.initCurrentUser(user);
	}

	public void signOut() throws AuthenticationException {
		final T user = securityContext.getCurrentUser();
		doSignOut(user);
		securityContext.removeCurrentUser();
	}

	private T doSignIn(final AuthenticationToken token) throws AuthenticationException {
		for (AuthenticationProvider<T> provider : providers) {
			final T user = provider.signIn(token);
			if (user != null) {
				return user;
			}
		}
		throw new AuthenticationException(String.format("Authentication token are not supported %s", token));
	}

	private void doSignOut(final T authentication) throws AuthenticationException {
		for (AuthenticationProvider<T> provider : providers) {
			provider.signOut(authentication);
		}
	}
}
