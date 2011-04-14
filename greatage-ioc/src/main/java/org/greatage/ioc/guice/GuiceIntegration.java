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

package org.greatage.ioc.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
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
public class GuiceIntegration implements Module {
	private final Set<ServiceDefinition<?>> services = CollectionUtils.newSet();

	public GuiceIntegration(final com.google.inject.Module... modules) {
		this(Guice.createInjector(modules));
	}

	@SuppressWarnings("unchecked")
	public GuiceIntegration(final Injector injector) {
		for (Key<?> key : injector.getBindings().keySet()) {
			services.add(new GuiceServiceDefinition(injector, key));
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
