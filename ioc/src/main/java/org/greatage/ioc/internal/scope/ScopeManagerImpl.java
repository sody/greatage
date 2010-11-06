/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.scope;

import org.greatage.ioc.IOCSymbols;
import org.greatage.ioc.annotations.Symbol;
import org.greatage.ioc.services.Scope;
import org.greatage.ioc.services.ScopeManager;
import org.greatage.util.StringUtils;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ScopeManagerImpl implements ScopeManager {
	private final Map<String, Scope> scopes;
	private final String defaultScope;

	public ScopeManagerImpl(final Map<String, Scope> scopes,
							@Symbol(IOCSymbols.DEFAULT_SCOPE) final String defaultScope) {
		assert scopes != null;
		assert defaultScope != null;

		this.scopes = scopes;
		this.defaultScope = defaultScope;
	}

	public Scope getScope(final String scope) {
		return scopes.get(StringUtils.isEmpty(scope) ? defaultScope : scope);
	}
}
