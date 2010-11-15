/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthenticationManagerImpl implements AuthenticationManager {
	private final List<AuthenticationProvider> providers;

	public AuthenticationManagerImpl(final List<AuthenticationProvider> providers) {
		this.providers = providers;
	}

	public Authentication authenticate(final AuthenticationToken token) throws AuthenticationException {
		for (AuthenticationProvider provider : providers) {
			if (provider.supports(token)) {
				return provider.authenticate(token);
			}
		}
		throw new AuthenticationException(String.format("Authentication token are not supported %s", token));
	}
}
