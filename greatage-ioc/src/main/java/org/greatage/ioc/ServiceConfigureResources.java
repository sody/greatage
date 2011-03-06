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

/**
 * This class represents service resources proxy that is used during service configure phase and adds service
 * configuration as additional resource.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceConfigureResources<T> extends ServiceAdditionalResources<T> {
	private final Object configuration;

	/**
	 * Creates new instance of service resources proxy for configure phase with defined service resources delegate and
	 * service configuration.
	 *
	 * @param delegate	  service resources delegate
	 * @param configuration service configuration
	 */
	ServiceConfigureResources(final ServiceResources<T> delegate, final Object configuration) {
		super(delegate);
		this.configuration = configuration;
	}

	/**
	 * {@inheritDoc} Gets service configuration instance as additional resource.
	 *
	 * @return service configuration or null
	 */
	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (resourceClass.isInstance(configuration)) {
			return resourceClass.cast(configuration);
		}
		return null;
	}
}
