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

package org.greatage.inject.internal.symbol;

import org.greatage.inject.services.SymbolProvider;

/**
 * This class represents {@link SymbolProvider} implementation that provides system properties as symbols for {@link
 * org.greatage.inject.services.SymbolSource}.
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
