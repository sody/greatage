/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.ioc.proxy.Invocation;
import org.greatage.ioc.proxy.MethodAdvice;
import org.greatage.security.annotations.Allow;
import org.greatage.security.annotations.Deny;
import org.greatage.security.annotations.Operation;
import org.greatage.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthoritySecurityAdvice implements MethodAdvice {
	private final SecurityContext securityContext;

	public AuthoritySecurityAdvice(final SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
		final Allow allow = invocation.getAnnotation(Allow.class);
		final Deny deny = invocation.getAnnotation(Deny.class);
		if (allow != null || deny != null) {
			final List<String> authorities = getAuthorities();
			if (allow != null) {
				checkAllow(allow, authorities);
			}
			if (deny != null) {
				checkDeny(deny, authorities);
			}
		}
		return invocation.proceed(parameters);
	}

	private void checkDeny(final Deny deny, final List<String> authorities) {
		if (deny.operation() == Operation.AND) {
			if (authorities.containsAll(Arrays.asList(deny.value()))) {
				throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
			}
		} else if (deny.operation() == Operation.OR) {
			for (String authority : deny.value()) {
				if (authorities.contains(authority)) {
					throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
				}
			}
		}
	}

	private void checkAllow(final Allow allow, final List<String> authorities) {
		if (allow.operation() == Operation.AND) {
			if (!authorities.containsAll(Arrays.asList(allow.value()))) {
				throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
			}
		} else if (allow.operation() == Operation.OR) {
			for (String authority : allow.value()) {
				if (authorities.contains(authority)) {
					return;
				}
			}
			throw new AccessDeniedException(String.format("Access denied for authorities: '%s'", authorities));
		}
	}

	private List<String> getAuthorities() {
		final Authentication user = securityContext.getCurrentUser();
		return user != null ? user.getAuthorities() : CollectionUtils.newList(AuthorityConstants.STATUS_ANONYMOUS);
	}
}
