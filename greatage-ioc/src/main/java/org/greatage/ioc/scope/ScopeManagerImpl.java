/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScopeManagerImpl implements ScopeManager {
	private final Map<String, Scope> scopes;

	public ScopeManagerImpl(final Map<String, Scope> scopes) {
		assert scopes != null;

		this.scopes = scopes;
	}

	public Scope getScope(final String scope) {
		return scopes.get(scope);
	}
}
