/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class PasswordAuthenticationProvider<T extends PasswordAuthentication>
		extends AbstractAuthenticationProvider<T, PasswordAuthenticationToken> {

	private final PasswordEncoder passwordEncoder;

	public PasswordAuthenticationProvider(final PasswordEncoder passwordEncoder) {
		super(PasswordAuthenticationToken.class);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected T doSignIn(final PasswordAuthenticationToken token) {
		final T authentication = getAuthentication(token.getName());
		if (authentication == null) {
			throw new AuthenticationException(String.format("Wrong name '%s'", token.getName()));
		}

		checkPassword(token.getPassword(), authentication.getPassword());
		return authentication;
	}

	protected abstract T getAuthentication(final String name);

	protected void checkPassword(final String password, final String expected) {
		final String actual = passwordEncoder.encode(password);
		if ((actual != null && !actual.equals(expected)) ||
				(actual == null && expected != null)) {
			throw new AuthenticationException(String.format("Wrong password '%s'", password));
		}
	}
}
