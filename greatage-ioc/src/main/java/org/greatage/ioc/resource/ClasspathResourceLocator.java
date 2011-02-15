/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.resource;

/**
 * This class represents {@link ResourceLocator} implementation that works with classpath resources.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClasspathResourceLocator extends AbstractResourceLocator {

	/**
	 * Creates new instance of classpath resource locator with defined root classpath resource.
	 */
	public ClasspathResourceLocator() {
		super(new ClasspathResource(Thread.currentThread().getContextClassLoader(), null, "", null));
	}
}
