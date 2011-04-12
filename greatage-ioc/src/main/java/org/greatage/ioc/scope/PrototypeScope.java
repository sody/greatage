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

import java.util.Map;

/**
 * This class represents {@link Scope} implementation that It is used for services that have different state for all
 * points where it is accessed. Default scope identifier is {@link ScopeConstants#PROTOTYPE}.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public class PrototypeScope extends AbstractScope {

	public PrototypeScope() {
		super(ScopeConstants.PROTOTYPE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cleanup() {
		//do nothing
	}

	@Override
	protected <S> void putService(final Marker<S> marker, final S service) {
		//do nothing
	}

	@Override
	protected <S> S getService(final Marker<S> marker) {
		return null;
	}

	@Override
	protected <S> boolean containsService(final Marker<S> marker) {
		return false;
	}

	@Override
	protected Map<Marker, Object> getScopeServices() {
		return null;
	}
}
