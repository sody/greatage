package org.greatage.ioc.internal.symbol;

import org.greatage.ioc.services.SymbolProvider;

import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class DefaultSymbolProvider implements SymbolProvider {
	public static final String PROVIDER_ID = "default";

	private final Map<String, String> symbols;

	public DefaultSymbolProvider(final Map<String, String> symbols) {
		assert symbols != null;

		this.symbols = symbols;
	}

	public String getSymbol(final String key) {
		return symbols.get(key);
	}
}
