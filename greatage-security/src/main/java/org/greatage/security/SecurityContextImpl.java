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

import org.greatage.util.CollectionUtils;

import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityContextImpl implements SecurityContext, AuthorityConstants {
	private final ThreadLocal<Authentication> currentUser = new ThreadLocal<Authentication>();
	private final Map<Authentication, Long> sessions = CollectionUtils.newConcurrentMap();
	private final long sessionExpirationPeriod = 1000000l;

	private final List<AuthenticationProvider> providers;

	public SecurityContextImpl(final List<AuthenticationProvider> providers) {
		this.providers = providers;
	}

	public Authentication getCurrentUser() {
		return currentUser.get();
	}

	public <T> T doAs(final Authentication authentication, final PrivilegedAction<T> action) {
		try {
			doStartSession(authentication);
			return action.run();
		} finally {
			doEndSession(authentication);
		}
	}

	public <T> T doAs(final Authentication authentication, final PrivilegedExceptionAction<T> action) throws Exception {
		try {
			doStartSession(authentication);
			return action.run();
		} finally {
			doEndSession(authentication);
		}
	}

	public void signIn(final AuthenticationToken token) {
		final Authentication authentication = doSignIn(token);
		doStartSession(authentication);
	}

	public void signOut() {
		final Authentication authentication = currentUser.get();
		doSignOut(authentication);
		doEndSession(authentication);
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

	private void doStartSession(final Authentication authentication) {
		if (authentication != null) {
			sessions.put(authentication, System.currentTimeMillis());
			currentUser.set(authentication);
		} else {
			currentUser.remove();
		}
	}

	private void doEndSession(final Authentication authentication) {
		if (authentication != null) {
			sessions.remove(authentication);
		}
		currentUser.remove();
	}
}
