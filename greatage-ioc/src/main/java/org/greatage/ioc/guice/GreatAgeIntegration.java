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

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import org.greatage.ioc.ServiceLocator;
import org.greatage.ioc.ServiceLocatorBuilder;
import org.greatage.ioc.ServiceProvider;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class GreatAgeIntegration implements Module {
	private final ServiceLocator locator;

	public GreatAgeIntegration(final Class... moduleClasses) {
		this(ServiceLocatorBuilder.createServiceLocator(moduleClasses));
	}

	public GreatAgeIntegration(final org.greatage.ioc.Module... modules) {
		this(ServiceLocatorBuilder.createServiceLocator(modules));
	}

	public GreatAgeIntegration(final ServiceLocator locator) {
		this.locator = locator;
	}

	@SuppressWarnings("unchecked")
	public void configure(final Binder binder) {
		for (String serviceId : locator.getServiceIds()) {
			final ServiceProvider<?> serviceStatus = locator.getServiceProvider(serviceId);
			binder.bind(serviceStatus.getServiceClass())
					.annotatedWith(Names.named(serviceStatus.getServiceId()))
					.toProvider(new GreatAgeProvider(serviceStatus));
		}
	}
}
