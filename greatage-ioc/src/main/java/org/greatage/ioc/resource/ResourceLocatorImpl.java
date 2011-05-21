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

package org.greatage.ioc.resource;

import org.greatage.util.CollectionUtils;
import org.greatage.util.PathUtils;

import java.util.List;
import java.util.Set;

/**
 * This class represents abstract {@link ResourceLocator} implementation that uses configured root resource for
 * retrieving other resources.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ResourceLocatorImpl implements ResourceLocator {

	private final List<ResourceProvider> resourceProviders;

	/**
	 * Creates new instance of resource locator with defined resource providers ordered configuration that will be
	 * used for retrieving resources.
	 *
	 * @param resourceProviders ordered configuration of resourcer providers
	 */
	public ResourceLocatorImpl(final List<ResourceProvider> resourceProviders) {
		this.resourceProviders = resourceProviders;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getResource(final String path) {
		for (ResourceProvider provider : resourceProviders) {
			final Resource resource = provider.getResource(path);
			if (resource != null) {
				return resource;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Resource> findResources(final String path, final String... includes) {
		final Set<String> includeSet = CollectionUtils.newSet(includes);
		return findResources(path, includeSet, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Resource> findResources(final String path, final Set<String> includes, final Set<String> excludes) {
		final Set<Resource> resources = CollectionUtils.newSet();

		final Resource parent = getResource(path);
		if (parent != null && parent.exists() && parent instanceof AbstractResource) {
			final String file = ((AbstractResource) parent).toURL().getFile();
			if (file != null) {
				final Set<String> resourceNames = PathUtils.findResources(file, includes, excludes);
				for (String resourceName : resourceNames) {
					final Resource resource = parent.getChild(resourceName);
					if (resource != null && resource.exists()) {
						resources.add(resource);
					}
				}
			}
		}
		return resources;
	}
}
