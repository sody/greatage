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

import org.greatage.inject.Interceptor;
import org.greatage.inject.Invocation;
import org.greatage.security.annotations.Allow;
import org.greatage.security.annotations.Deny;
import org.greatage.security.annotations.Operation;
import org.greatage.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AuthoritySecurityAdvice implements Interceptor {
	private final SecurityContext securityContext;

	public AuthoritySecurityAdvice(final SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public boolean supports(final Invocation invocation) {
		final Method method = invocation.getMethod();
		return method.isAnnotationPresent(Allow.class) || method.isAnnotationPresent(Deny.class);
	}

	public Object invoke(final Invocation invocation, final Object... parameters) throws Throwable {
		final Method method = invocation.getMethod();
		final Allow allow = method.getAnnotation(Allow.class);
		final Deny deny = method.getAnnotation(Deny.class);
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
