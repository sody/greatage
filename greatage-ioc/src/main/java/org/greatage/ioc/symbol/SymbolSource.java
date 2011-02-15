/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.symbol;

/**
 * This interface represents an service that obtains string values by their names. It also understands expressions like
 * <tt>${another symbol}</tt>.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface SymbolSource {

	/**
	 * Obtains string value by specified expression. It understands expressions like <tt>${another symbol}</tt>.
	 *
	 * @param expression symbol expression
	 * @return string value
	 */
	String getValue(String expression);
}
