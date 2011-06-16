/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.ioc.internal.symbol;

import org.greatage.ioc.services.SymbolProvider;

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
