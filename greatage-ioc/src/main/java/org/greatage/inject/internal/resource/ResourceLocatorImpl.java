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

package org.greatage.inject.internal.resource;

import org.greatage.inject.services.Resource;
import org.greatage.inject.services.ResourceLocator;
import org.greatage.inject.services.ResourceProvider;
import org.greatage.util.CollectionUtils;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * This class represents abstract {@link ResourceLocator} implementation that uses configured root resource for retrieving other
 * resources.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ResourceLocatorImpl implements ResourceLocator {

	private final List<ResourceProvider> resourceProviders;

	/**
	 * Creates new instance of resource locator with defined resource providers ordered configuration that will be used for
	 * retrieving resources.
	 *
	 * @param resourceProviders ordered configuration of resource providers
	 */
	public ResourceLocatorImpl(final List<ResourceProvider> resourceProviders) {
		this.resourceProviders = resourceProviders;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getResource(final String path) {
		assert path != null : "Resource path cannot be null";

		for (ResourceProvider provider : resourceProviders) {
			final Resource resource = provider.getResource(path);
			if (resource != null && resource.exists()) {
				return resource;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getResource(final String location, final String name, final String type, final Locale locale) {
		assert name != null : "Resource name cannot be null";

		for (ResourceProvider provider : resourceProviders) {
			final Resource resource = provider.getResource(location, name, type, locale);
			if (resource != null && resource.exists()) {
				return resource;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Resource> findResources(final String path, final String... includes) {
		final Resource baseResource = getResource(path);
		return baseResource != null ? baseResource.children(includes) : CollectionUtils.<Resource>newSet();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Resource> findResources(final String path, final Set<String> includes, final Set<String> excludes) {
		final Resource baseResource = getResource(path);
		return baseResource != null ? baseResource.children(includes, excludes) : CollectionUtils.<Resource>newSet();
	}
}
