/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.internal.symbol;

import org.greatage.ioc.services.SymbolProvider;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SystemSymbolProvider implements SymbolProvider {
	public static final String PROVIDER_ID = "system";

	public String getSymbol(final String key) {
		return System.getProperty(key);
	}
}
