/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.symbol;

/**
 * This class represents {@link SymbolProvider} implementation that provides system properties as symbols for {@link
 * SymbolSource}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SystemSymbolProvider implements SymbolProvider {

	/**
	 * {@inheritDoc} Provides system properties.
	 */
	public String getSymbol(final String key) {
		return System.getProperty(key);
	}
}
