/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.ioc.proxy.Invocation;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.security.annotations.Authority;
import org.greatage.security.annotations.Permission;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityAdvice implements MethodAdvice {
	private final SecurityChecker securityChecker;

	public SecurityAdvice(final SecurityChecker securityChecker) {
		this.securityChecker = securityChecker;
	}

	public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
		final Authority authority = invocation.getAnnotation(Authority.class);
		if (authority != null) {
			securityChecker.checkAuthority(authority.value());
		}
		final Permission permission = invocation.getAnnotation(Permission.class);
		if (permission != null) {
			for (Object parameter : parameters) {
				securityChecker.checkPermission(parameter, permission.value());
			}
		}
		return invocation.proceed(parameters);
	}
}
