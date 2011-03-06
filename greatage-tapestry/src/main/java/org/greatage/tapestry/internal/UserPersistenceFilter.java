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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.services.*;
import org.greatage.security.AnonymousAuthenticationToken;
import org.greatage.security.Authentication;
import org.greatage.security.AuthenticationManager;
import org.greatage.security.SecurityContext;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserPersistenceFilter implements RequestFilter {
	private static final String SECURITY_CONTEXT_KEY = "SECURITY_CONTEXT";

	private final AuthenticationManager authenticationManager;
	private final SecurityContext securityContext;

	public UserPersistenceFilter(final AuthenticationManager authenticationManager, final SecurityContext securityContext) {
		this.authenticationManager = authenticationManager;
		this.securityContext = securityContext;
	}

	@SuppressWarnings({"unchecked"})
	public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
		try {
			final Authentication authentication = loadAuthentication(request);
			if (authentication != null) {
				securityContext.initCurrentUser(authentication);
			} else {
				authenticationManager.signIn(new AnonymousAuthenticationToken());
			}
			return handler.service(request, response);
		} finally {
			final Authentication authentication = securityContext.getCurrentUser();
			securityContext.clearCurrentUser();
			saveAuthentication(request, authentication);
		}
	}

	private Authentication loadAuthentication(final Request request) {
		final Session session = getSession(request, true);
		final Object user = session != null ? session.getAttribute(SECURITY_CONTEXT_KEY) : null;
		return user != null && user instanceof Authentication ? (Authentication) user : null;
	}

	private void saveAuthentication(final Request request, final Authentication user) {
		final Session session = getSession(request, true);
		session.setAttribute(SECURITY_CONTEXT_KEY, user);
	}

	private Session getSession(final Request request, final boolean allowCreate) {
		final Session session = request.getSession(false);
		return session == null && allowCreate ? request.getSession(true) : session;
	}
}
