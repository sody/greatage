/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(AuthenticationManager.class, AuthenticationManagerImpl.class);
		binder.bind(SecurityContext.class, SecurityContextImpl.class);
	}
}
