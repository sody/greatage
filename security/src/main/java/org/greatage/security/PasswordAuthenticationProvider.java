/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class PasswordAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;

	public PasswordAuthenticationProvider(final PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public boolean supports(final AuthenticationToken token) {
		return token != null && token instanceof PasswordAuthenticationToken;
	}

	public Authentication signIn(final AuthenticationToken token) throws AuthenticationException {
		if (!supports(token)) {
			return null;
		}

		final PasswordAuthenticationToken authenticationToken = (PasswordAuthenticationToken) token;
		return doSignIn(authenticationToken.getName(), authenticationToken.getPassword());
	}

	public void signOut(final Authentication authentication) throws AuthenticationException {
		// do nothing
	}

	protected abstract PasswordAuthentication getAuthentication(final String name);

	protected Authentication doSignIn(final String name, final String password) {
		final PasswordAuthentication authentication = getAuthentication(name);
		if (authentication == null) {
			throw new AuthenticationException(String.format("Wrong name '%s'", name));
		}

		checkPassword(password, authentication.getPassword());
		return authentication;
	}

	protected void checkPassword(final String password, final String expected) {
		final String actual = passwordEncoder.encode(password);
		if ((actual != null && !actual.equals(expected)) ||
				(actual == null && expected != null)) {
			throw new AuthenticationException(String.format("Wrong password '%s'", password));
		}
	}
}
