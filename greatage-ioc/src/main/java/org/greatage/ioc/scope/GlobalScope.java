/*
 * Copyright 2011 Ivan Khalopik
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

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link Scope} implementation that is used for services that have the same state for whole
 * application. Default scope identifier is {@link ScopeConstants#GLOBAL}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GlobalScope extends AbstractScope {
	private final Map<String, Object> services = CollectionUtils.newConcurrentMap();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, Object> getServices() {
		return services;
	}
}
