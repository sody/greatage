/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.scope;

import java.util.Map;

/**
 * This class represents default {@link ScopeManager} implementation that obtains scope instances by their name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScopeManagerImpl implements ScopeManager {
	private final Map<String, Scope> scopes;

	/**
	 * Creates new instance of scope manager with defined mapped configuration of scopes.
	 *
	 * @param scopes scope instances mapped by their identifiers
	 */
	public ScopeManagerImpl(final Map<String, Scope> scopes) {
		assert scopes != null;

		this.scopes = scopes;
	}

	/**
	 * {@inheritDoc}
	 */
	public Scope getScope(final String scope) {
		return scopes.get(scope);
	}
}
