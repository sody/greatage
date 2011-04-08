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

import java.lang.reflect.Constructor;

/**
 * This class represent default implementation service definition that automatically instantiates service. It is based on building
 * service by invoking service implementation constructor.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public class ServiceDefinitionImpl<T> implements ServiceDefinition<T> {
	private final Marker<T> marker;
	private final Class<? extends T> implementationClass;
	private final String scope;
	private final boolean override;

	private final Logger logger;

	/**
	 * Creates new instance of service definition with defined service identifier, service class, scope and override option.
	 *
	 * @param logger	   system logger
	 * @param serviceClass service class
	 * @param scope		service scope
	 * @param override	 option that determines is service overrides its default definition
	 */
	ServiceDefinitionImpl(final Logger logger,
						  final Class<T> serviceClass,
						  final String scope,
						  final boolean override) {
		this(logger, serviceClass, serviceClass, scope, override);
	}

	/**
	 * Creates new instance of service definition with defined service identifier, service class, service implementation class, scope
	 * and override option.
	 *
	 * @param logger			  system logger
	 * @param serviceClass		service class
	 * @param implementationClass service implementation class
	 * @param scope			   service scope
	 * @param override			option that determines is service overrides its default definition
	 */
	ServiceDefinitionImpl(final Logger logger,
						  final Class<T> serviceClass,
						  final Class<? extends T> implementationClass,
						  final String scope,
						  final boolean override) {
		this(logger, Marker.generate(serviceClass, implementationClass), scope, override);
	}

	ServiceDefinitionImpl(final Logger logger,
						  final Marker<T> marker,
						  final String scope,
						  final boolean override) {
		this.logger = logger;
		this.marker = marker;
		this.scope = scope;
		this.override = override;
		this.implementationClass = marker.getTargetClass();
	}

	public Marker<T> getMarker() {
		return marker;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isOverride() {
		return override;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * {@inheritDoc} It automatically builds service instance by invoking service implementation constructor.
	 */
	public T build(final ServiceResources<T> resources) {
		logger.info("Building service (%s) from (%s)", resources.getMarker(), implementationClass);

		try {
			final Constructor constructor = implementationClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(resources, constructor);
			return implementationClass.cast(constructor.newInstance(parameters));
		}
		catch (Exception e) {
			throw new ApplicationException(String.format("Can't create service (%s)", resources.getMarker()), e);
		}
	}
}
