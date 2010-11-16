/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.tapestry.internal;

import org.apache.tapestry5.services.*;
import org.greatage.ioc.ServiceLocator;
import org.greatage.security.Authentication;
import org.greatage.security.UserContext;

import java.io.IOException;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class UserPersistenceFilter implements RequestFilter {
	private static final String SECURITY_CONTEXT_KEY = "SECURITY_CONTEXT";

	private final ServiceLocator serviceLocator;

	public UserPersistenceFilter(final ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public boolean service(final Request request, final Response response, final RequestHandler handler) throws IOException {
		final UserContext context = serviceLocator.getService(UserContext.class);
		try {
			final Authentication authentication = getAuthentication(request);
			context.setUser(authentication);
			return handler.service(request, response);
		} finally {
			final Authentication authentication = context.getUser();
			context.setUser(null);
			setAuthentication(request, authentication);
		}
	}

	private Authentication getAuthentication(final Request request) {
		final Session session = getSession(request, true);
		final Object user = session != null ? session.getAttribute(SECURITY_CONTEXT_KEY) : null;
		return user != null && user instanceof Authentication ? (Authentication) user : null;
	}

	private void setAuthentication(final Request request, final Authentication user) {
		final Session session = getSession(request, true);
		session.setAttribute(SECURITY_CONTEXT_KEY, user);
	}

	private Session getSession(final Request request, final boolean allowCreate) {
		final Session session = request.getSession(false);
		return session == null && allowCreate ? request.getSession(true) : session;
	}
}
