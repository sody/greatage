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
public abstract class AbstractAuthenticationProvider<A extends Authentication, T extends AuthenticationToken>
		implements AuthenticationProvider {

	private final Class<A> authenticationClass;
	private final Class<T> tokenClass;

	protected AbstractAuthenticationProvider(final Class<A> authenticationClass, final Class<T> tokenClass) {
		this.authenticationClass = authenticationClass;
		this.tokenClass = tokenClass;
	}

	@SuppressWarnings({"unchecked"})
	public A signIn(final AuthenticationToken token) throws AuthenticationException {
		if (supports(token)) {
			return doSignIn((T) token);
		}

		return null;
	}

	@SuppressWarnings({"unchecked"})
	public void signOut(final Authentication authentication) throws AuthenticationException {
		if (supports(authentication)) {
			doSignOut((A) authentication);
		}
	}

	protected boolean supports(final AuthenticationToken token) {
		return token != null && tokenClass.equals(token.getClass());
	}

	protected boolean supports(final Authentication authentication) {
		return authentication != null && authenticationClass.equals(authentication.getClass());
	}

	protected abstract A doSignIn(final T token);

	protected abstract void doSignOut(final A authentication);
}
