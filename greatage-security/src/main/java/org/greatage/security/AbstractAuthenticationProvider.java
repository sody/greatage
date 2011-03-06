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
public abstract class AbstractAuthenticationProvider<T extends Authentication, E extends AuthenticationToken>
		implements AuthenticationProvider<T> {

	private final Class<E> supportedTokenClass;

	protected AbstractAuthenticationProvider(final Class<E> supportedTokenClass) {
		this.supportedTokenClass = supportedTokenClass;
	}

	@SuppressWarnings({"unchecked"})
	public T signIn(final AuthenticationToken token) throws AuthenticationException {
		if (supports(token)) {
			return doSignIn((E) token);
		}

		return null;
	}

	public void signOut(final T authentication) throws AuthenticationException {
		//do nothing by default
	}

	protected boolean supports(final AuthenticationToken token) {
		return token != null && supportedTokenClass.isAssignableFrom(token.getClass());
	}

	protected abstract T doSignIn(final E token);

}
