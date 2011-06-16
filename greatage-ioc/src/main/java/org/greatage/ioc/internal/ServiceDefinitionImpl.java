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

package org.greatage.ioc.internal;

import org.greatage.ioc.ApplicationException;
import org.greatage.ioc.Marker;
import org.greatage.util.DescriptionBuilder;

import java.lang.reflect.Constructor;

/**
 * This class represent default implementation service definition that automatically instantiates service. It is based
 * on building service by invoking service implementation constructor.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceDefinitionImpl<T> implements ServiceDefinition<T> {
	private final Marker<T> marker;
	private final Class<? extends T> implementationClass;
	private final boolean override;
	private final boolean eager;

	ServiceDefinitionImpl(final Marker<T> marker,
						  final Class<? extends T> implementationClass,
						  final boolean override,
						  final boolean eager) {
		this.marker = marker;
		this.implementationClass = implementationClass;
		this.override = override;
		this.eager = eager;
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

	public boolean isEager() {
		return eager;
	}

	/**
	 * {@inheritDoc} It automatically builds service instance by invoking service implementation constructor.
	 */
	public T build(final ServiceResources<T> resources) {
		try {
			final Constructor constructor = implementationClass.getConstructors()[0];
			final Object[] parameters = InternalUtils.calculateParameters(resources, constructor);
			return implementationClass.cast(constructor.newInstance(parameters));
		} catch (Exception e) {
			throw new ApplicationException(String.format("Can't create service (%s)", resources.getMarker()), e);
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("service", implementationClass);
		return builder.toString();
	}
}
