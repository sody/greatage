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

/**
 * This class represents service resources proxy that is used during service decorate phase and adds service instance as
 * additional resource.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceDecorateResources<T> extends ServiceAdditionalResources<T> {
	private final T serviceInstance;

	/**
	 * Creates new instance of service resources proxy for decorate phase with defined service resource delegate and
	 * service instance.
	 *
	 * @param delegate		service resources delegate
	 * @param serviceInstance service instance
	 */
	ServiceDecorateResources(final ServiceResources<T> delegate, final T serviceInstance) {
		super(delegate);
		this.serviceInstance = serviceInstance;
	}

	/**
	 * Gets service instance.
	 *
	 * @return service instance
	 */
	public T getServiceInstance() {
		return serviceInstance;
	}

	/**
	 * {@inheritDoc} Gets service instance as additional resource.
	 *
	 * @return service instance or null
	 */
	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (resourceClass.isInstance(serviceInstance)) {
			return resourceClass.cast(serviceInstance);
		}
		return null;
	}
}
