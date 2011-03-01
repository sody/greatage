/*
 * Copyright 2011 Ivan Khalopik
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
import org.greatage.util.OrderingUtils;

import java.util.List;

/**
 * This class represents service builder that is used for service creation by its service, contributors and decorators
 * definitions.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBuilder<T> implements ObjectBuilder<T> {
	private final Service<T> service;
	private final ServiceResources<T> resources;
	private final List<Contributor<T>> contributors;
	private final List<Decorator<T>> decorators;

	/**
	 * Creates new instance of service builder with defined initial service resources, service, contributors and decorators
	 * definitions.
	 *
	 * @param resources	initial service resources
	 * @param service	  service definition
	 * @param contributors service contributor definitions
	 * @param decorators   service decorator definitions
	 */
	ServiceBuilder(final ServiceResources<T> resources,
				   final Service<T> service,
				   final List<Contributor<T>> contributors,
				   final List<Decorator<T>> decorators) {
		this.service = service;
		this.resources = resources;
		this.contributors = contributors;
		this.decorators = decorators;
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
		final T serviceInstance = service.build(buildResources);
		return decorateService(serviceInstance);
	}

	/**
	 * Implements decorate phase of service building.
	 *
	 * @param delegate service instance
	 * @return decorated service instance
	 * @throws ApplicationException when decorator returns the same service instance or null
	 */
	private T decorateService(final T delegate) {
		final List<Decorator<T>> ordered = OrderingUtils.order(decorators);
		T decoratedService = delegate;
		for (Decorator<T> decorator : ordered) {
			final ServiceDecorateResources<T> decorateResources =
					new ServiceDecorateResources<T>(resources, decoratedService);
			decoratedService = decorator.decorate(decorateResources);
			if (decoratedService == null || decoratedService.equals(decorateResources.getServiceInstance())) {
				throw new ApplicationException("Decorator returns the same instance");
			}
		}
		return decoratedService;
	}
}
