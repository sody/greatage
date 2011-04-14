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
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.apache.tapestry5.ioc.services.ServiceActivity;
import org.apache.tapestry5.ioc.services.ServiceActivityScoreboard;
import org.greatage.ioc.Marker;
import org.greatage.ioc.Module;
import org.greatage.ioc.ServiceDefinition;
import org.greatage.ioc.ServiceContributor;
import org.greatage.ioc.ServiceInterceptor;
import org.greatage.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TapestryIntegration implements Module {
	private final Set<ServiceDefinition<?>> services = CollectionUtils.newSet();

	public TapestryIntegration(final Class... moduleClasses) {
		this(RegistryBuilder.buildAndStartupRegistry(moduleClasses));
	}

	@SuppressWarnings("unchecked")
	public TapestryIntegration(final Registry registry) {
		final ServiceActivityScoreboard scoreboard = registry.getService(ServiceActivityScoreboard.class);
		for (ServiceActivity activity : scoreboard.getServiceActivity()) {
			services.add(new TapestryServiceDefinition(registry, activity.getServiceId(), activity.getServiceInterface()));
		}
	}

	public Collection<ServiceDefinition<?>> getDefinitions() {
		return services;
	}

	public <T> List<ServiceContributor<T>> getContributors(final Marker<T> marker) {
		return CollectionUtils.newList();
	}

	public <T> List<ServiceInterceptor<T>> getInterceptors(final Marker<T> marker) {
		return CollectionUtils.newList();
	}
}
