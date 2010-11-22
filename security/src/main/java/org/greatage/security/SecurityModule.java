/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.ioc.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(AuthenticationManager.class, AuthenticationManagerImpl.class);
		binder.bind(SecurityChecker.class, AuthoritySecurityChecker.class).withId("AuthoritySecurityChecker");
		binder.bind(SecurityContext.class, SecurityContextImpl.class).withScope(ScopeConstants.THREAD);
	}

	@Build(scope = ScopeConstants.PROTOTYPE)
	public Authentication buildAuthentication(final SecurityContext context) {
		return context.getAuthentication();
	}
}
