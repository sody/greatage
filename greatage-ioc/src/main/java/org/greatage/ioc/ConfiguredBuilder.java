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
 * This class represents service builder that is used for service creation by its service, contributors and decorators
 * definitions.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ConfiguredBuilder<T> implements ObjectBuilder<T> {
	private final ServiceDefinition<T> service;
	private final ServiceResources<T> resources;
	private final List<ServiceContributor<T>> contributors;

	/**
	 * Creates new instance of service builder with defined initial service resources, service, contributors and decorators
	 * definitions.
	 *
	 * @param resources	initial service resources
	 * @param service	  service definition
	 * @param contributors service contributor definitions
	 */
	ConfiguredBuilder(final ServiceResources<T> resources,
					  final ServiceDefinition<T> service,
					  final List<ServiceContributor<T>> contributors) {
		this.service = service;
		this.resources = resources;
		this.contributors = contributors;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getObjectClass() {
		return service.getServiceClass();
	}

	/**
	 * Builds, configures and decorates service instance. It builds new service instance every time when invoked.
	 *
	 * @return ready for use service instance
	 */
	public T build() {
		final ServiceResources<T> buildResources = new ServiceBuildResources<T>(resources, contributors);
		return service.build(buildResources);
	}
}
