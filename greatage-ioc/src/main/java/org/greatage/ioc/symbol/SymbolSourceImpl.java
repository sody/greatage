/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.symbol;

import org.greatage.ioc.ApplicationException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents default {@link SymbolSource} implementation that obtains string values by their names. It also
 * understands expressions like <tt>${another symbol}</tt>.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SymbolSourceImpl implements SymbolSource {
	private static final String VARIABLE_PREFIX = "\\$\\{";
	private static final String VARIABLE_SUFFIX = "\\}";
	private static final String VARIABLE_REGEX = ".*" + VARIABLE_PREFIX + "(\\S+)" + VARIABLE_SUFFIX + ".*";
	private static final Pattern VARIABLE_PATTERN = Pattern.compile(VARIABLE_REGEX);

	private final List<SymbolProvider> providers;

	/**
	 * Creates new instance of symbol source service with defined ordered configuration of symbol providers.
	 *
	 * @param providers ordered configuration of symbol providers
	 */
	public SymbolSourceImpl(final List<SymbolProvider> providers) {
		assert providers != null;

		this.providers = providers;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getValue(final String expression) {
		Matcher matcher = VARIABLE_PATTERN.matcher(expression);
		if (!matcher.matches()) {
			return getSymbol(expression);
		}

		String result = expression;
		while (matcher.matches()) {
			final String key = matcher.group(1);
			result = result.replaceAll(VARIABLE_PREFIX + key + VARIABLE_SUFFIX, getSymbol(key));
			matcher = VARIABLE_PATTERN.matcher(result);
		}
		return result;
	}

	/**
	 * Gets symbol value by specified symbol name. It uses all configured symbol providers to obtain symbol value.
	 *
	 * @param key symbol name
	 * @return symbol value, not null
	 * @throws ApplicationException when symbol with specified name is not found
	 */
	private String getSymbol(final String key) {
		for (SymbolProvider provider : providers) {
			final String symbol = provider.getSymbol(key);
			if (symbol != null) {
				return symbol;
			}
		}
		throw new ApplicationException("Missing variable: " + key);
	}
}
