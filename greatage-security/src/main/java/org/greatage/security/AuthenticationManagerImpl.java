/*
 * Copyright 2011 Ivan Khalopik
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
