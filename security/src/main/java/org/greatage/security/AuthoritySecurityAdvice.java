/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.ioc.proxy.Invocation;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.security.annotations.Authority;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthoritySecurityAdvice implements MethodAdvice {
	private final SecurityChecker securityChecker;

	public AuthoritySecurityAdvice(final SecurityChecker securityChecker) {
		this.securityChecker = securityChecker;
	}

	public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
		final Authority authority = invocation.getAnnotation(Authority.class);
		if (authority != null) {
			securityChecker.check(null, authority.value());
		}
		return invocation.proceed(parameters);
	}
}
