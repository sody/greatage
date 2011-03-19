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

import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link Scope} implementation that is used for services that have the same state inside one
 * application thread. Default scope identifier is {@link ScopeConstants#THREAD}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ThreadScope extends AbstractScope {
	private final ThreadLocal<Map<String, Object>> services = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return CollectionUtils.newConcurrentMap();
		}
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cleanup() {
		super.cleanup();
		services.remove();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, Object> getServices() {
		return services.get();
	}
}
