/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.symbol;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultSymbolProvider implements SymbolProvider {
	private final Map<String, String> symbols;

	public DefaultSymbolProvider(final Map<String, String> symbols) {
		assert symbols != null;

		this.symbols = symbols;
	}

	public String getSymbol(final String key) {
		return symbols.get(key);
	}
}
