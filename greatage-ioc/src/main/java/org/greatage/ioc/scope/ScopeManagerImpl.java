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

package org.greatage.ioc.scope;

import org.greatage.ioc.ApplicationException;
import org.greatage.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * This class represents default {@link ScopeManager} implementation that obtains scope instances by their name.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ScopeManagerImpl implements ScopeManager {
	private final Map<String, Scope> scopes = CollectionUtils.newMap();

	/**
	 * Creates new instance of scope manager with defined mapped configuration of scopes.
	 *
	 * @param scopes scope instances mapped by their identifiers
	 */
	public ScopeManagerImpl(final Collection<Scope> scopes) {
		assert scopes != null;

		for (Scope scope : scopes) {
			this.scopes.put(scope.getName(), scope);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Scope getScope(final String scope) {
		if (!scopes.containsKey(scope)) {
			throw new ApplicationException(String.format("Cannot find scope with name '%s'", scope));
		}

		return scopes.get(scope);
	}

	public Collection<Scope> getScopes() {
		return scopes.values();
	}
}
