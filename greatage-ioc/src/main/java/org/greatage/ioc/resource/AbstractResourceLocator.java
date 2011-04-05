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

/**
 * This class represents abstract {@link ResourceLocator} implementation that uses configured root resource for
 * retrieving other resources.
 *
 * @author Ivan Khalopik
 * @since 1.1
 */
public abstract class AbstractResourceLocator implements ResourceLocator {
	private final Resource rootResource;

	/**
	 * Creates new instance of resource locator with defined root resource that will be used for retrieving other
	 * resources.
	 *
	 * @param rootResource root resource
	 */
	protected AbstractResourceLocator(final Resource rootResource) {
		this.rootResource = rootResource;
	}

	/**
	 * {@inheritDoc}
	 */
	public Resource getResource(final String path) {
		return rootResource.getChild(path);
	}
}
