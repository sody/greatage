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

	public void signIn(final AuthenticationToken token) throws AuthenticationException {
		final Authentication user = doSignIn(token);
		securityContext.setCurrentUser(user);
	}

	public void signOut() throws AuthenticationException {
		doSignOut(securityContext.getCurrentUser());
		securityContext.setCurrentUser(null);
	}

	private Authentication doSignIn(final AuthenticationToken token) throws AuthenticationException {
		for (AuthenticationProvider provider : providers) {
			final Authentication user = provider.signIn(token);
			if (user != null) {
				return user;
			}
		}
		throw new AuthenticationException(String.format("Authentication token are not supported %s", token));
	}

	private void doSignOut(final Authentication authentication) throws AuthenticationException {
		for (AuthenticationProvider provider : providers) {
			provider.signOut(authentication);
		}
	}
}
