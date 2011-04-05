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

import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.scope.ScopeConstants;

import java.lang.annotation.Annotation;

/**
 * This class represents default {@link ServiceBindingOptions} implementation that is used to define service unique id,
 * service scope and is it overrides the existing service.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceBindingOptionsImpl<T> implements ServiceBindingOptions {
	private final Class<T> serviceClass;
	private final Class<? extends T> implementationClass;

	private String serviceScope;
	private boolean override;

	/**
	 * Creates new instance of service binding options with defined service class and service implementation class.
	 *
	 * @param serviceClass		service class
	 * @param implementationClass service implementation class
	 */
	ServiceBindingOptionsImpl(final Class<T> serviceClass, final Class<? extends T> implementationClass) {
		this.serviceClass = serviceClass;
		this.implementationClass = implementationClass;
		this.serviceScope = ScopeConstants.GLOBAL;
		this.override = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceBindingOptions withScope(final String scope) {
		serviceScope = scope;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceBindingOptions override() {
		override = true;
		return this;
	}

	/**
	 * Creates new instance of configured service definition.
	 *
	 * @param logger system logger
	 * @return new instance of configured service definition, not null
	 */
	public ServiceDefinition<T> createService(final Logger logger) {
		return new ServiceDefinitionImpl<T>(logger, serviceClass, implementationClass, serviceScope, override);
	}
}
