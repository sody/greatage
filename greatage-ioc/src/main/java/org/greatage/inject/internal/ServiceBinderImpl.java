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

package org.greatage.inject.internal;

import org.greatage.inject.ServiceBinder;
import org.greatage.inject.ServiceBindingOptions;
import org.greatage.inject.services.ServiceDefinition;
import org.greatage.util.CollectionUtils;

import java.util.List;

/**
 * This class represents default {@link org.greatage.inject.ServiceBinder} implementation that distributively binds service
 * interfaces to their automatically built instances. It is based on binding services by invoking module static method.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBinderImpl implements ServiceBinder {
	private final List<ServiceBindingOptionsImpl> services = CollectionUtils.newList();

	/**
	 * Creates new instance of service binder that distributively binds service interfaces to their automatically built
	 * instances.
	 */
	ServiceBinderImpl() {
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> ServiceBindingOptions bind(final Class<T> implementationClass) {
		return bind(implementationClass, implementationClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> ServiceBindingOptions bind(final Class<T> serviceClass, final Class<? extends T> implementationClass) {
		final ServiceBindingOptionsImpl<T> options =
				new ServiceBindingOptionsImpl<T>(serviceClass, implementationClass);
		services.add(options);
		return options;
	}

	/**
	 * Creates list of configured automatically built service definitions.
	 *
	 * @return list of configured automatically built service definitions or empty list
	 */
	public List<ServiceDefinition<?>> createServices() {
		final List<ServiceDefinition<?>> result = CollectionUtils.newList();
		for (ServiceBindingOptionsImpl options : services) {
			result.add(options.createService());
		}
		return result;
	}
}
