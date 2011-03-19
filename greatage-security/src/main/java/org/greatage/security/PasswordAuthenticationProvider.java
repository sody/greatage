/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
