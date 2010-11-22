/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.security;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SecurityContextImpl implements SecurityContext {
	private Authentication authentication;

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(final Authentication authentication) {
		this.authentication = authentication;
	}
}
