/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthoritySecurityChecker implements SecurityChecker {
	private final SecurityContext securityContext;

	public AuthoritySecurityChecker(final SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public void check(final Object securedObject, final String authority) {
		final List<String> authorities = getAuthorities();
		if (!authorities.contains(authority)) {
			throw new AccessDeniedException(String.format("Access denied. Needed authority missed: '%s'", authority));
		}
	}

	private List<String> getAuthorities() {
		final Authentication user = securityContext.getAuthentication();
		return user != null ? user.getAuthorities() : CollectionUtils.<String>newList();
	}
}
