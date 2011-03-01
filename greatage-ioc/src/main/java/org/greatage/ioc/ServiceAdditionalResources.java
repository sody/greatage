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

import java.lang.annotation.Annotation;

/**
 * This class represents {@link ServiceResources} proxy implementation that is used for extending its default
 * functionality by adding additional resources. Its implementations are used for different service build phases.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class ServiceAdditionalResources<T> implements ServiceResources<T> {
	private final ServiceResources<T> delegate;

	/**
	 * Creates new instance of service resources proxy with defined service resources delegate instance.
	 *
	 * @param delegate service resources delegate instance
	 */
	ServiceAdditionalResources(final ServiceResources<T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Gets service resources delegate instance. It is used to delegate almost all methods of {@link ServiceResources}
	 * interface.
	 *
	 * @return service resources delegate instance
	 */
	protected ServiceResources<T> getDelegate() {
		return delegate;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceId() {
		return delegate.getServiceId();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<T> getServiceClass() {
		return delegate.getServiceClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceScope() {
		return delegate.getServiceScope();
	}

	/**
	 * {@inheritDoc} Firstly tries to retrieve resource from additional resources.
	 */
	public <E> E getResource(final Class<E> resourceClass, final Annotation... annotations) {
		final E resource = getAdditionalResource(resourceClass);
		return resource != null ? resource : delegate.getResource(resourceClass, annotations);
	}

	/**
	 * Gets additional service resource by type. It is used for extending {@link ServiceResources} default functionality by
	 * adding additional resource retrieving strategy.
	 *
	 * @param resourceClass resource class
	 * @param <E>           resource type
	 * @return additional service resource for specified type or null
	 */
	protected abstract <E> E getAdditionalResource(final Class<E> resourceClass);
}
