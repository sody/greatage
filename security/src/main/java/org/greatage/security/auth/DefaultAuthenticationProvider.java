/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security.auth;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class DefaultAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;

	public DefaultAuthenticationProvider(final PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public boolean supports(final AuthenticationToken token) {
		return token != null && token instanceof DefaultAuthenticationToken;
	}

	public Authentication authenticate(final AuthenticationToken token) throws AuthenticationException {
		final DefaultAuthenticationToken authenticationToken = (DefaultAuthenticationToken) token;
		return doAuthenticate(authenticationToken.getName(), authenticationToken.getPassword());
	}

	private Authentication doAuthenticate(final String name, final String password) {
		final DefaultAuthentication authentication = getAuthentication(name);
		if (authentication == null) {
			throw new AuthenticationException(String.format("Wrong name '%s'", name));
		}

		checkPassword(password, authentication.getPassword());
		return authentication;
	}

	protected abstract DefaultAuthentication getAuthentication(final String name);

	protected void checkPassword(final String password, final String expected) {
		final String actual = passwordEncoder.encode(password);
		if ((actual != null && !actual.equals(expected)) ||
				(actual == null && expected != null)) {
			throw new AuthenticationException(String.format("Wrong password '%s'", password));
		}
	}
}
