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

package org.greatage.inject.internal.scope;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Marker;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.Scope;
import org.greatage.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * This class represents abstract {@link Scope} implementation that look ups service instance inside the scope and if it
 * is not found creates new instance using specified service builder.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractScope implements Scope {
	private final Map<Marker, Object> services = CollectionUtils.newMap();

	private final Class<? extends Annotation> key;
	private final ProxyFactory proxyFactory;

	protected AbstractScope(final Class<? extends Annotation> key, final ProxyFactory proxyFactory) {
		this.key = key;
		this.proxyFactory = proxyFactory;
	}

	public Class<? extends Annotation> getKey() {
		return key;
	}

	public <T> T get(final Marker<T> marker) {
		return marker.getServiceClass().cast(services.get(marker));
	}

	public <T> void register(final Marker<T> marker, final ObjectBuilder<T> builder, final Interceptor interceptor) {
		final CachedBuilder<T> cachedBuilder = new CachedBuilder<T>(builder);
		final T proxy = proxyFactory.createProxy(marker.getServiceClass(), cachedBuilder, interceptor);
		services.put(marker, proxy);
	}
}
