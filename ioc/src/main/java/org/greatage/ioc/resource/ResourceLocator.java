/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

/**
 * This interface represents utility producing resources by path.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ResourceLocator {

	/**
	 * Obtains a resource by its path.
	 *
	 * @param path resource path
	 * @return resource instance
	 */
	Resource getResource(String path);

}
