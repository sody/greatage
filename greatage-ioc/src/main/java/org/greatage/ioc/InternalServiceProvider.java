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

package org.greatage.ioc;

import org.greatage.ioc.proxy.ObjectBuilder;

import java.util.List;

/**
 * This class represents implementation of {@link ServiceProvider} that is used for internal services. Such services
 * have specific scope - something similar with global and can not be intercepted. They include {@link
 * org.greatage.ioc.proxy.ProxyFactory}, {@link org.greatage.ioc.logging.LoggerSource}, {@link
 * org.greatage.ioc.scope.ScopeManager}.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class InternalServiceProvider<T> implements ServiceProvider<T> {
	private final ServiceResources<T> resources;
	private final ObjectBuilder<T> builder;
	private T serviceInstance;

	/**
	 * Creates new instance of internal service status with defined service locator, service definition, contribute
	 * definitions and decorate definitions.
	 *
	 * @param locator	  service locator
	 * @param service	  service definition
	 * @param contributors service contribute definitions
	 */
	InternalServiceProvider(final ServiceLocator locator,
							final ServiceDefinition<T> service,
							final List<ServiceContributor<T>> contributors) {
		this.resources = new ServiceInitialResources<T>(locator, service);
		this.builder = new ConfiguredBuilder<T>(resources, service, contributors);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceId() {
		return resources.getServiceId();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getServiceClass() {
		return resources.getServiceClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceScope() {
		return resources.getServiceScope();
	}

	/**
	 * Gets service instance and creates new if it is not built yet.
	 *
	 * @return service instance
	 */
	public T getService() {
		if (serviceInstance == null) {
			serviceInstance = builder.build();
		}
		return serviceInstance;
	}
}
