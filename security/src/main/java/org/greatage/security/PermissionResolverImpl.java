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
public class PermissionResolverImpl implements PermissionResolver {
	private final UserContext userContext;
	private final AccessControlManager accessControlManager;

	public PermissionResolverImpl(final UserContext userContext, final AccessControlManager accessControlManager) {
		this.userContext = userContext;
		this.accessControlManager = accessControlManager;
	}

	public boolean isGranted(final Object securedObject, final String permission) {
		final Authentication user = userContext.getUser();
		final List<String> authorities = user != null ? user.getAuthorities() : CollectionUtils.<String>newList();
		final AccessControlList acl = accessControlManager.getAccessControlList(securedObject);
		for (String authority : authorities) {
			final AccessControlEntry ace = acl.getAccessControlEntry(authority, permission);
			if (ace.isGranted()) {
				return true;
			}
		}
		return false;
	}
}
