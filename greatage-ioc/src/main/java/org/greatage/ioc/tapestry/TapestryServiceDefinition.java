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

package org.greatage.ioc.tapestry;

import org.apache.tapestry5.ioc.Registry;
import org.greatage.ioc.Key;
import org.greatage.ioc.Marker;
import org.greatage.ioc.internal.ServiceDefinition;
import org.greatage.ioc.internal.ServiceResources;
import org.greatage.ioc.annotations.Prototype;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TapestryServiceDefinition<T> implements ServiceDefinition<T> {
	private final Registry registry;
	private final String serviceId;
	private final Marker<T> marker;

	TapestryServiceDefinition(final Registry registry, final String serviceId, final Class<T> serviceClass) {
		this.registry = registry;
		this.serviceId = serviceId;
		marker = Key.get(serviceClass).named(serviceId).scoped(Prototype.class);
	}

	public Marker<T> getMarker() {
		return marker;
	}

	public boolean isOverride() {
		return false;
	}

	public boolean isEager() {
		return false;
	}

	public T build(final ServiceResources<T> resources) {
		return registry.getService(serviceId, marker.getServiceClass());
	}
}
