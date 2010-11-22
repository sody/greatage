/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.ioc.proxy.Invocation;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.security.annotations.Permission;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class PermissionSecurityAdvice implements MethodAdvice {
	private final SecurityChecker securityChecker;

	public PermissionSecurityAdvice(final SecurityChecker securityChecker) {
		this.securityChecker = securityChecker;
	}

	public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
		final Permission permission = invocation.getAnnotation(Permission.class);
		if (permission != null) {
			for (Object parameter : parameters) {
				securityChecker.check(parameter, permission.value());
			}
		}
		return invocation.proceed(parameters);
	}
}
