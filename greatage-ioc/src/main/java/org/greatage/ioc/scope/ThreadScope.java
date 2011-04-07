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

import org.greatage.ioc.Marker;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.util.CollectionUtils;

import java.util.Map;

/**
 * This class represents {@link Scope} implementation that is used for services that have the same state inside one application
 * thread. Default scope identifier is {@link ScopeConstants#THREAD}.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ThreadScope extends AbstractScope {
	private final ThreadLocal<Map<Marker, Object>> services = new ThreadLocal<Map<Marker, Object>>() {
		@Override
		protected Map<Marker, Object> initialValue() {
			return CollectionUtils.newConcurrentMap();
		}
	};

	public ThreadScope() {
		super(ScopeConstants.THREAD);
	}

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
	protected Map<Marker, Object> getScopeServices() {
		return services.get();
	}
}
