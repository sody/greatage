package org.greatage.ioc.internal.symbol;

import org.greatage.ioc.services.SymbolProvider;
import org.greatage.ioc.services.SymbolSource;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SymbolSourceImpl implements SymbolSource {
	private static final String VARIABLE_PREFIX = "\\$\\{";
	private static final String VARIABLE_SUFFIX = "\\}";
	private static final Pattern VARIABLE_PATTERN = Pattern.compile(".*" + VARIABLE_PREFIX + "(\\S+)" + VARIABLE_SUFFIX + ".*");

	private final List<SymbolProvider> providers;

	public SymbolSourceImpl(final List<SymbolProvider> providers) {
		assert providers != null;

		this.providers = providers;
	}

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

	private String getSymbol(final String key) {
		for (SymbolProvider provider : providers) {
			final String symbol = provider.getSymbol(key);
			if (symbol != null) {
				return symbol;
			}
		}
		throw new RuntimeException("Messing variable: " + key);
	}
}
