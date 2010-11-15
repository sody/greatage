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
	private final UserContext userContext;

	public AuthenticationManagerImpl(final List<AuthenticationProvider> providers, final UserContext<?> userContext) {
		this.providers = providers;
		this.userContext = userContext;
	}

	@SuppressWarnings({"unchecked"})
	public Authentication authenticate(final AuthenticationToken token) throws AuthenticationException {
		final Authentication authentication = doAuthenticate(token);
		userContext.setUser(authentication);
		return authentication;
	}

	private Authentication doAuthenticate(final AuthenticationToken token) {
		for (AuthenticationProvider provider : providers) {
			if (provider.supports(token)) {
				return provider.authenticate(token);
			}
		}
		throw new AuthenticationException(String.format("Authentication token are not supported %s", token));
	}
}
