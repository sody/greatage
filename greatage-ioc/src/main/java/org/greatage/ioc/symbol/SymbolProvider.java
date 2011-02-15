/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.symbol;

/**
 * This class represents configuration point of {@link org.greatage.ioc.symbol.SymbolSource} and it produces symbol
 * values by their names.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface SymbolProvider {

	/**
	 * Gets symbol value by specified symbol name.
	 *
	 * @param key symbol name
	 * @return string value
	 */
	String getSymbol(String key);
}
