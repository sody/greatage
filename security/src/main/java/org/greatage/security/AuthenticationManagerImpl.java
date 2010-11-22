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
	private final SecurityContext securityContext;

	public AuthenticationManagerImpl(final List<AuthenticationProvider> providers, final SecurityContext securityContext) {
		this.providers = providers;
		this.securityContext = securityContext;
	}

	@SuppressWarnings({"unchecked"})
	public Authentication authenticate(final AuthenticationToken token) throws AuthenticationException {
		final Authentication authentication = doAuthenticate(token);
		securityContext.setAuthentication(authentication);
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
