/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractResourceLocator implements ResourceLocator {
	private final Resource rootResource;

	protected AbstractResourceLocator(final Resource rootResource) {
		this.rootResource = rootResource;
	}

	public Resource getResource(final String path) {
		return rootResource.getChild(path);
	}
}
