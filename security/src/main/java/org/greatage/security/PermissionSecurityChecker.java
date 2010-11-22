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
public class PermissionSecurityChecker implements SecurityChecker {
	private final SecurityContext securityContext;
	private final AccessControlManager accessControlManager;

	public PermissionSecurityChecker(final SecurityContext securityContext, final AccessControlManager accessControlManager) {
		this.securityContext = securityContext;
		this.accessControlManager = accessControlManager;
	}

	public void check(final Object securedObject, final String permission) {
		final List<String> authorities = getAuthorities();
		final AccessControlList acl = accessControlManager.getAccessControlList(securedObject);
		for (String authority : authorities) {
			final AccessControlEntry ace = acl.getAccessControlEntry(authority, permission);
			if (ace.isGranted()) {
				return;
			}
		}
		throw new AccessDeniedException(String.format("Access denied for object %s. Need permission missed: '%s'", securedObject, permission));
	}

	private List<String> getAuthorities() {
		final Authentication user = securityContext.getAuthentication();
		return user != null ? user.getAuthorities() : CollectionUtils.<String>newList();
	}
}
