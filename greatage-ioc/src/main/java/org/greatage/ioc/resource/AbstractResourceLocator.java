/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

/**
 * This class represents abstract {@link ResourceLocator} implementation that uses configured root resource for
 * retrieving other resources.
 *
 * @author Ivan Khalopik
 * @since 1.0
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
