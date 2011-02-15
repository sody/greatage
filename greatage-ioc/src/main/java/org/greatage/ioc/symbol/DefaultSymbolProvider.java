/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.symbol;

import java.util.Map;

/**
 * This class represents configurable {@link SymbolProvider} implementation that gets symbols from configured map.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultSymbolProvider implements SymbolProvider {
	private final Map<String, String> symbols;

	/**
	 * Creates new instance of symbol provider with specified map of configured symbols.
	 *
	 * @param symbols mapped symbol configuration
	 */
	public DefaultSymbolProvider(final Map<String, String> symbols) {
		assert symbols != null;

		this.symbols = symbols;
	}

	/**
	 * {@inheritDoc} Gets symbols from configured map.
	 */
	public String getSymbol(final String key) {
		return symbols.get(key);
	}
}
